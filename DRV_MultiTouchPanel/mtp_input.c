#include <linux/kernel.h>
#include <linux/module.h>
#include <linux/platform_device.h>
#include <linux/i2c.h>
#include <linux/err.h>
#include <linux/slab.h>
#include <linux/interrupt.h>
#include <linux/input.h>
#include <linux/irq.h>
#include <asm/mach/irq.h>

#include <linux/gpio.h>
#include <mach/gpio.h>
#include <plat/gpio-cfg.h>


#define MTP_ADDR (0x70 >> 1)
#define MTP_MAX_X  800
#define MTP_MAX_Y  480

#define MTP_IRQ  gpio_to_irq(EXYNOS4_GPX1(6))

#define MTP_NAME "ft5x0x_ts"
#define MTP_MAX_ID 15


struct input_dev *ts_dev;
static struct work_struct mtp_work;
static struct i2c_client *mtp_client;

struct mtp_event {
	int x;
	int y;
	int id;
};

static struct mtp_event mtp_events[16];
static int mtp_points;

static irqreturn_t mtp_interrupt(int irq, void *dev_id) {
	schedule_work(&mtp_work);
	
	return IRQ_HANDLED;
}

static int mtp_ft5x0x_i2c_rxdata(struct i2c_client *client, char *rxdata, int length) {
	int ret;
	struct i2c_msg msgs[] = {
			{
					.addr	= client->addr,
					.flags	= 0,
					.len	= 1,
					.buf	= rxdata,
			},
			{
					.addr	= client->addr,
					.flags	= I2C_M_RD,
					.len	= length,
					.buf	= rxdata,
			},
	};

	ret = i2c_transfer(client->adapter, msgs, 2);
	if (ret < 0)
		pr_err("%s: i2c read error: %d\n", __func__, ret);

	return ret;
}

static int mtp_ft5x0x_read_data(void) {
	u8 buf[32] = { 0 };
	int ret;

	ret = mtp_ft5x0x_i2c_rxdata(mtp_client, buf, 31);

	if (ret < 0) {
		printk("%s : read touch data failed, %d\n", __func__, ret);
		return ret;
	}

	mtp_points = buf[2] & 0x0f;

	switch (mtp_points) {
		case 5:
			mtp_events[4].x = (s16)(buf[0x1b] & 0x0F) << 8 | (s16)buf[0x1c];
			mtp_events[4].y = (s16)(buf[0x1d] & 0x0F) << 8 | (s16)buf[0x1e];
			mtp_events[4].id = buf[0x1d] >> 4;
		case 4:
			mtp_events[3].x = (s16)(buf[0x15] & 0x0F) << 8 | (s16)buf[0x16];
			mtp_events[3].y = (s16)(buf[0x17] & 0x0F) << 8 | (s16)buf[0x18];
			mtp_events[3].id = buf[0x17] >> 4;
		case 3:
			mtp_events[2].x = (s16)(buf[0x0f] & 0x0F) << 8 | (s16)buf[0x10];
			mtp_events[2].y = (s16)(buf[0x11] & 0x0F) << 8 | (s16)buf[0x12];
			mtp_events[2].id = buf[0x11] >> 4;
		case 2:
			mtp_events[1].x = (s16)(buf[0x09] & 0x0F) << 8 | (s16)buf[0x0a];
			mtp_events[1].y = (s16)(buf[0x0b] & 0x0F) << 8 | (s16)buf[0x0c];
			mtp_events[1].id = buf[0x0b] >> 4;
		case 1:
			mtp_events[0].x = (s16)(buf[0x03] & 0x0F) << 8 | (s16)buf[0x04];
			mtp_events[0].y = (s16)(buf[0x05] & 0x0F) << 8 | (s16)buf[0x06];
			mtp_events[0].id = buf[0x05] >> 4;
			break;
		case 0:
			return 0;
		default:
			//printk("%s: invalid touch data, %d\n", __func__, event->touch_point);
			return -1;
	}

	return 0;
}

static void mtp_work_func(struct work_struct *work) {
	int i;
	int ret;

	ret = mtp_ft5x0x_read_data();
	if (ret < 0) {
		return;
	}
	if (!mtp_points) {
		input_mt_sync(ts_dev);
		input_sync(ts_dev);
		return;
	}
	
	for (i = 0; i < mtp_points; i++) {
		input_report_abs(ts_dev, ABS_MT_POSITION_X, mtp_events[i].x);
		input_report_abs(ts_dev, ABS_MT_POSITION_Y, mtp_events[i].y);
		input_report_abs(ts_dev, ABS_MT_TRACKING_ID, mtp_events[i].id);
		input_mt_sync(ts_dev);
	 }

	input_sync(ts_dev);
}

static int __devinit mtp_probe(struct i2c_client *client, const struct i2c_device_id *id) {
	printk("%s %s %d\n", __FILE__, __FUNCTION__, __LINE__);
	
	mtp_client = client;

	ts_dev = input_allocate_device();
	
	set_bit(EV_SYN, ts_dev->evbit);
	set_bit(EV_ABS, ts_dev->evbit);
	set_bit(INPUT_PROP_DIRECT, ts_dev->propbit);
	
	set_bit(ABS_MT_TRACKING_ID, ts_dev->absbit);
	set_bit(ABS_MT_POSITION_X, ts_dev->absbit);
	set_bit(ABS_MT_POSITION_Y, ts_dev->absbit);
	
	input_set_abs_params(ts_dev, ABS_MT_TRACKING_ID, 0, MTP_MAX_ID, 0, 0);
	input_set_abs_params(ts_dev, ABS_MT_POSITION_X, 0, MTP_MAX_X, 0, 0);
	input_set_abs_params(ts_dev, ABS_MT_POSITION_Y, 0, MTP_MAX_Y, 0, 0);
	
	ts_dev->name = MTP_NAME;
	
	input_register_device(ts_dev);
	
	INIT_WORK(&mtp_work, mtp_work_func);
	
	request_irq(MTP_IRQ, mtp_interrupt,
			IRQ_TYPE_EDGE_FALLING, "100ask_mtp", ts_dev);
	
	return 0;
}

static int __devexit mtp_remove(struct i2c_client *client) {
	printk("%s %s %d\n", __FILE__, __FUNCTION__, __LINE__);
	
	free_irq(MTP_IRQ, ts_dev);
	cancel_work_sync(&mtp_work);
	
	input_unregister_device(ts_dev);
	input_free_device(ts_dev);
	
	return 0;
}

static const struct i2c_device_id mtp_id_table[] = {
	{ "100ask_mtp", 0 },
	{ "ft5x0x_ts", 0 },
	{}
};

static int mtp_ft5x06_valid(struct i2c_client *client) {
	u8 buf[32] = { 0 };
	int ret;

	printk("mtp_ft5x06_valid : addr = 0x%x\n", client->addr);

	buf[0] = 0xa3;
	ret = mtp_ft5x0x_i2c_rxdata(client, buf, 1);

	if(ret < 0) {
		printk("There is not real device, i2c read err\n");
		return ret;
	}
	printk("chip vendor id = 0x%x\n", buf[0]);

	if (buf[0] != 0x55) {
		printk("There is no real device, val err\n");
		return -1;
	}

	return 0;
}

static int mtp_detect(struct i2c_client *client, struct i2c_board_info *info) {
	printk("mtp_detect : addr = 0x%x\n", client->addr);
	
	if (mtp_ft5x06_valid(client) < 0)
		return -1;

	strlcpy(info->type, "100ask_mtp", I2C_NAME_SIZE);
	
	return 0;
}

static const unsigned short addr_list[] = { MTP_ADDR, I2C_CLIENT_END };

static struct i2c_driver mtp_driver = {
	.class = I2C_CLASS_HWMON,
	.driver = {
		.name = "100ask",
		.owner = THIS_MODULE,
	},
	.probe = mtp_probe,
	.remove = __devexit_p(mtp_remove),
	.id_table = mtp_id_table,
	.detect = mtp_detect,
	.address_list = addr_list
};

static int __init mtp_drv_init(void)
{
	return i2c_add_driver(&mtp_driver);
}

static void __exit mtp_drv_exit(void)
{
	i2c_del_driver(&mtp_driver);
}

module_init(mtp_drv_init);
module_exit(mtp_drv_exit);

MODULE_LICENSE("GPL");

