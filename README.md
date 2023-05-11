# MultiDroolsDemo

A simple project to demonstrate the use of drools in a multi-step process.  The java code will calculate some compensation on either a compensable event or an event allocation, including an eligible to calc, rate determination, and finally comp calculation.  The key points of making it work within the drools is the use of `insert` and `update` to manage objects in working memory on which the rules will operate.
