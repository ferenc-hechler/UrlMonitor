# UrlMonitor
Simple long time Monitor for a http-url, logging status into a logfile with timestamps.

usage: java -jar urlMonitor.jar <http-url> <logfile> <loop-delay-seconds> <timeout-seconds>
example: java -jar urlMonitor.jar http://yourserver/index.html urlmonitor.log 15 15

example for the logfile output:
```
[----- urlmonitor.log -----]
URL:     http://yoursite/index.html
Log:     urlmonitor.log
timeout: 15s
delay:   15s


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
 
T stands for Timeout
H stand for Host not found
X stands for connect error
```
 
