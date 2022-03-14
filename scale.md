You can scale the application in two way:

1) **Horizontal Scale**
I am using docker image in this project. You can deploy this image to Kubernets. If needed , Kubernets will replicate your docker containers and run new insatances.That's why your application scale horizontal easily.
Also we can use an API gateway with load balancer. LB will balance the incoming loads and provide better performance. 
We should use rate limitter for requests a client can make per second (rate) and per day/week/month (quota).

If caching is not enough to reduce the volume of work on our database, one can switch from using a single database to horizontal scaling, where the application data is stored across multiple DB instances.
While it is challenging manual work, cloud services provide many solutions to achieve distributed database systems.

2) **Vertical Scale**

A simple way to scale your software is to add on more hardware.(CPU,Ram,Hard Disk ...)
Faster processors and more memory will certainly grow your software, but you'll need space and hardware continually, which gets expensive quickly.
Also you we can optimize the code for improving the aplication performance.
