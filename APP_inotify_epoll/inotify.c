#include <unistd.h>
#include <stdio.h>
#include <sys/inotify.h>
#include <string.h>
#include <errno.h>

int read_process_inotify_fd(int fd) {
    int res;
    char event_buf[512];
    int event_size;
    int event_pos = 0;
    struct inotify_event *event;

    res = read(fd, event_buf, sizeof(event_buf));

    if (res < (int) sizeof(*event)) {
        if (errno == EINTR)
            return 0;
        printf("could not get event, %s\n", strerror(errno));
        return -1;
    }

    while (res >= (int) sizeof(*event)) {
        event = (struct inotify_event *)(event_buf + event_pos);
        if (event->len) {
            if (event->mask & IN_CREATE) {
                printf("create file: %s\n", event->name);
            } else {
                printf("delete file: %s\n", event->name);
            }
        }
        event_size = sizeof(*event) + event->len;
        res -= event_size;
        event_pos += event_size;
    }

    return 0;
}

int main(int argc, char **argv) {
    int mINotifyFd;
    int result;

    if (argc != 2) {
        printf("Usage: %s <dir>\n", argv[0]);
        return -1;
    }

    mINotifyFd = inotify_init();

    result = inotify_add_watch(mINotifyFd, argv[1], IN_DELETE | IN_CREATE);

    while (1) {
        read_process_inotify_fd(mINotifyFd);
    }
	return 0;
}