# back-to-the-roots

The idea for this project is to learn how the Rest Server works underneath all the frameworks, and how small can we write it, if we just drop the frameworks alltogether.

The way I'm going to do this, is by copying my [the basic version of my Eregold project](https://github.com/DigitalCrafting/eregold/tree/master/01-basic-web-app), and one by one going lower and lower in the implementation, each in separate directory. 
For now, the frontend and database of this application will be the same throughout the project, only the backend will be rewritten. 

Steps:
1. Spring Boot app - copied from [Eregold](https://github.com/DigitalCrafting/eregold/tree/master/01-basic-web-app)
2. Java com.sun.net.httpserver.HttpServer (no server like Tomcat, just java)
3. Java using sockets
4. C/C++ version
5. C/C++ with assembly snippets 

Up until step 3 we will be checking how little code we actually need for a web application, further steps are basically learning C/C++, with added bonus of performance, since I will not have Garbage Collector. I want to see how hard is it to actually write working software with manual memory management. 
