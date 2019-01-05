# UrlMonitor
Simple java tool for monitoring a http(s)-url, checking status or regular expression. Logging status with timestamps.

usage: java -jar urlMonitor.jar \<http-url\> [\<logfile\> \<loop-delay-seconds\> \<timeout-seconds\> \<regex-check\>\]

example: java -jar urlMonitor.jar "http://yourserver/index.html" "urlmonitor.log" 15 15 "STATUS=200"

example for the logfile output:

```
[----- urlmonitor.log -----]
UrlMonitor
==========
URL:     http://yoursite/index.html
Log:     urlmonitor.log
delay:   15s
timeout: 15s
RegEx:   STATUS=200


21:54:28: ............................................................
21:54:50: ......T[21:55:06]HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH
21:55:14: HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH
21:55:23: HHHHHHHHHHHHHHHHHHHHH.......................................
21:55:34: .....................T[21:56:00]HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH
21:56:05: HHHHHHHHHHHHHHHHHHHHHHHHHHHHH......X[21:56:13]XXXXXXXXXXXXXXXXXXXXXXXX
21:56:17: XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX.....T[21:56:42]HHHHHHHHHHHHHHHH
21:56:56: HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH
21:57:06: HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH....
21:57:15: ............................................................
21:57:35: ............................................................
21:57:55: ............................................................
21:58:14: ............................................................
21:58:30: ............................................................
21:58:44: ............................................................
21:58:54: ............................................................
[--------------------------]
 
T stands for SocketTimeoutException
H stand for UnknownHostException
X stands for "Network is unreachable: connect" error message
? stands for regex not matching
```

As default the regex checks for STATUS=200.
But also any text of the page content can be checked.
E.g "`<div><span id=ct class=h1>[0-2][0-9]:[0-5][13579]:[0-5][0-9]</span>`" will check for odd minutes on a site providing the current time.

 
```
> java -jar urlmonitor.jar https://www.timeanddate.com/worldclock/germany/frankfurt urlmonitor.log 15 15 "<div><span id=ct class=h1>[0-2][0-9]:[0-5][13579]:[0-5][0-9]</span>"



UrlMonitor
=========
URL:     https://www.timeanddate.com/worldclock/germany/frankfurt
Log:     urlmonitor.log
delay:   15s
timeout: 15s
RegEx:   <div><span id=ct class=h1>[0-2][0-9]:[0-5][13579]:[0-5][0-9]</span>


05.01.2019 23:34:40: ??....???....????....????...????....???....????....????....?
05.01.2019 23:50:33: ??....????....??
```


The tool can also be used to dump the whole website content using the keyword "DUMP" instead of a regex.
The html is output in one very long line (the example is shortened using '...').

```
UrlMonitor
==========
URL:     http://yourserver/index.html
Log:     urlmonitor.log
delay:   15s
timeout: 15s
RegEx:   DUMP


05.01.2019 23:01:51: STATUS=200 <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"><html><head><title>Home</title><meta http-equiv="Content-Type" content="text/html; ...</body></html> 
05.01.2019 23:01:51: STATUS=200 <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"><html><head><title>Home</title><meta http-equiv="Content-Type" content="text/html; ...</body></html> 
05.01.2019 23:02:22: STATUS=200 <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"><html><head><title>Home</title><meta http-equiv="Content-Type" content="text/html; ...</body></html> 
05.01.2019 23:02:54: STATUS=200 <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"><html><head><title>Home</title><meta http-equiv="Content-Type" content="text/html; ...</body></html> 
05.01.2019 23:03:09: STATUS=200 <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"><html><head><title>Home</title><meta http-equiv="Content-Type" content="text/html; ...</body></html> 
05.01.2019 23:03:24: STATUS=200 <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"><html><head><title>Home</title><meta http-equiv="Content-Type" content="text/html; ...</body></html> 
05.01.2019 23:03:39: STATUS=200 <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"><html><head><title>Home</title><meta http-equiv="Content-Type" content="text/html; ...</body></html> 
```
