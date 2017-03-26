/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

#define LOG_TAG "LedHal"

#include <hardware/led_hal.h>
#include <hardware/hardware.h>

#include <cutils/log.h>

#include <stdio.h>
#include <unistd.h>
#include <fcntl.h>
#include <errno.h>
#include <math.h>


static int fd;

static int led_open(struct led_device_t* device) {
	fd = open("/dev/leds", O_RDWR);
	ALOGI("led_open : %d", fd);
	if (fd >= 0)
		return 0;
	else
		return -1;
}

static int led_ctrl(struct led_device_t* device, int which, int status) {
	int ret = ioctl(fd, status, which);
	ALOGI("led_ctrl : %d, %d, %d", which, status, ret);
	return ret;
}

static int led_close(struct hw_device_t* device)
{
	ALOGI("led_close : %d", fd);
    close(fd);
    return 0;
}

static struct led_device_t led_dev = {
	.common = {
		.tag   = HARDWARE_DEVICE_TAG,
		.close = led_close,
	},
	.led_open  = led_open,
	.led_ctrl  = led_ctrl,
};

static int  led_device_open(const struct hw_module_t* module, const char* id,
        struct hw_device_t** device) {
	*device = &led_dev;
	return 0;
}

static struct hw_module_methods_t led_module_methods = {
    .open = led_device_open,
};

struct hw_module_t HAL_MODULE_INFO_SYM = {
    .tag = HARDWARE_MODULE_TAG,
    .id = "led",
    .methods = &led_module_methods,
};
