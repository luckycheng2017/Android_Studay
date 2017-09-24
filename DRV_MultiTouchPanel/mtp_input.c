#include <linux/kernel.h>
#include <linux/module.h>
#include <linux/platform_device.h>
#include <linux/i2c.h>
#include <linux/err.h>
#include <linux/regmap.h>
#include <linux/slab.h>


#define MTP_ADDR 0x70
#define MTP_MAX_X  800
#define MTP_MAX_Y  480

#define MTP_IRQ  123

#define MTP_NAME "xxxx"
#define MTP_MAX_ID 1000

struct input_dev *ts_dev;
static struct work_struct mtp_work;;

static irqreturn_t mtp_interrupt(int irq, void *dev_id) {
	schedule_work(&mtp_work);
	
	return IRQ_HANDLED;
}

static void mtp_work_func(struct work_struct *work) {
	if (/* 没有触点 */) {
		input_mtp_sync(ts_dev);
		input_sync(ts_dev);
		return;
	}
	
	for (/* 每一个点 */) {
	    input_report_abs(ts_dev, ABS_MT_POSITION_X, x);
	    input_report_abs(ts_dev, ABS_MT_POSITION_Y, y);
	    input_report_abs(ts_dev, ABS_MT_TRACKING_ID, id);
	    input_mt_sync(ts_dev);	
	 }

	input_sync(ts_dev);
}

static int __devinit mtp_probe(struct i2c_client *client, const struct i2c_device_id *id) {
	printk("%s %s %d\n", __FILE__, __FUNCTION__, __LINE__)；
	
	ts_dev = input_allocate_device();
	
	set_bit(EV_SYN, ts_dev->evbit);
	set_bit(EV_ADB, ts_dev->evbit);
	
	set_bit(ABS_MT_TRACKING_ID, ts_dev->absbit);
	set_bit(ABS_MT_POSITION_X, ts_dev->absbit);
	set_bit(ABS_MT_POSITION_Y, ts_dev->adsbit);
	
	input_set_ads_params(ts_dev, ABS_MT_TRACKING_ID, 0, MTP_MAX_ID, 0, 0);
	input_set_abs_params(ts_dev, ABS_MT_POSITION_X, 0, MTP_MAX_X, 0, 0);
	input_set_abs_params(ts_dev, ABS_MT_POSITION_Y, 0, MTP_MAX_Y, 0, 0);
	
	ts_dev->name = MTP_NAME;
	
	input_register_device(ts_dev);
	
	INIT_WORK(&mtp_work, mtp_work_func);
	
	request_irq(MTP_IRQ, mtp_interrupt, IRQ_TYPE_EDGE_FALLING, "100ask_mtp", ts_dev);
	
	return 0;
}

static int __devexit mtp_remove(struct i2c_client *client) {
	printk("%s %s %d\n", __FILE__, __FUNCTION__, __LINE__);
	
	free_irq(MTP_IRQ, ts_dev);
	cancel_work_sync(%mtp_work);
	
	input_unregister_device(ts_dev);
	input_free_device(ts_dev);
	
	return 0;
}

static const struct i2c_device_id mtp_id_table[] = {
	{ "100ask_mtp", 0 },
	{}
};

static int mtp_detect(struct i2c_client *client, struct i2c_board_info *info) {
	printk("mtp_detect : addr = 0x%x\n", client->addr);
	
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

