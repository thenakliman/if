This library helps to improve code readability of if else statements. Following examples shows its usage


1. __*If else syntax for expression*__

+ If an expression is true then user `x` otherwise use `y`
    ```
    int allowedSpeed;
    if(currentRoad == "highWay") {
        allowedSpeed = 160km/h;
    } else {
        allowedSpeed = 40km/h;    
    }
    
    setSpeed(allowedSpeed);
    ```
    
    Simplified as
    ```
    int allowedSpeed = if.isTrue(currentRoad == "highWay")
                         .thenReturn(160km/h)
                         .elseReturn(40km/h);
    setSpeed(allowedSpeed);

+ If an expression is true then process something otherwise use some other process
    ```
    if(currentLocation == "known") {
        driveWithFun();
    } else {
        informPassenger();    
    }
    ```
    
    Simplified as
    ```
    int allowedSpeed = if.isTrue(currentRoad == "known")
                         .thenCall(() -> driveWithFun())
                         .elseCall(() -> informPassenger());
    ```

+ If an expression is true then get a value from one mechanism otherwise from a different one 
    ```
    int allowedSpeed;
    if(allowedSpeedAtCurrentLocation == "unknown") {
        allowedSpeed = callAFriendAndGetIt();
    } else {
        allowedSpeed = getItFromMyBrain();
    }
    
    accelerateToGivenSpeed(allowedSpeed)
    ```
    
    Simplified as
    ```
    int allowedSpeed = if.isTrue(allowedSpeedAtCurrentLocation == "unknown")
                         .thenGet(() -> callAFriendAndGetIt())
                         .elseGet(() -> getItFromMyBrain());
    accelerateToGivenSpeed(allowedSpeed)
    ```

+ If something unexpected occurred and need to raise exception else process
    ```
    if(currentSpeed == "unacceptable-level") {
        throw new UnacceptableSpeed(currentSpeed);
    } else {
        informSpeedIsAcceptable();
    }
    
    driveSmoothly(allowedSpeed)
    ```
    
    Simplified as
    ```
    if.isTrue(currentSpeed == "unacceptable-level")
      .thenThrow(() -> new UnacceptableSpeed(currentSpeed))
      .elseCall(() -> informSpeedIsAcceptable());
    driveSmoothly(allowedSpeed)
    ```
    
    all the above methods could be used in combinations, please refer test cases for all possible scenarios

2. __*If else for handling Null object*__

+ when a value is calculated one way, if a variable is null and other way if variable is not null
    ```
    String boxMessage;
    if(box == null) {
        boxMessage = "Box exist";
    } else {
        boxMessage = "Box does not exist";
    }
    
    showMessage(boxMessage)
    ```
    
    Simplified as
    ```
    String boxMessage = if.isNull(box)
                          .thenReturn("Box exist")
                          .elseReturn("Box does not exist");
    showMessage(boxMessage);
    ```

+ A value is calculated if a variable is null otherwise use given value,
    ```
    if(boxMessage == null) {
        boxMessage = generateMessage();
    }
    
    showMessage(boxMessage);
    ```
    
    Simplified version:
    ```
    String boxMessage = if.isNull(boxMessage)
                          .thenGet(() -> generateMessage())
                          .elseReturn(boxMessage);
    
    showMessage(boxMessage);
    ```

+ A value is calculated, if a variable is null one way, otherwise use some other way,
    ```
    if(receivedBoxMessage == null) {
        boxMessage = generateMessage();
    } else {
        boxMessage = generateMessageOtherWay();
    }
    
    showMessage(boxMessage);
    ```
    
    Simplified version:
    ```
    String boxMessage = if.isNull(receivedBoxMessage)
                          .thenGet(() -> generateMessage())
                          .elseGet(() -> generateMessageOtherWay());
    
    showMessage(boxMessage);
    ```

+ A value is calculated one way, if a variable is null, otherwise use non null value to generate value,
    ```
    if(receivedBoxMessage == null) {
        boxMessage = generateMessage();
    } else {
        boxMessage = generateMessage(boxMessage);
    }
    
    showMessage(boxMessage);
    ```
    
    Simplified version:
    ```
    String boxMessage = if.isNull(receivedBoxMessage)
                          .thenGet(() -> generateMessage())
                          .elseMap((nonNullReceivedBoxMessage) -> generateMessage(nonNullReceivedBoxMessage));
    
    showMessage(boxMessage);
    ```

+ If you would like to throw an exception, if a value is null, otherwise proceed,
    ```
    if(boxMessage == null) {
        throw new NotFoundException("Message not found");
    } else {
        boxMessage = generateMessage(boxMessage);
    }
    
    showMessage(boxMessage)
    ```

    Simplified version:
    ```
    String boxMessage = if.isNull(receivedBoxMessage)
                          .thenThrow(new NotFoundException("Message not found"))
                          .elseMap((nonNullReceivedBoxMessage) -> generateMessage(nonNullReceivedBoxMessage));
    
    showMessage(boxMessage)
    ```

and so on. see test cases to know more about available options.

3. __*do something only if some condition is true, else part is empty*__

+. If you like to process something when condition is true else nothing

    ```
    if(purseStatus == "not found) {
        putCashInPocket(cash);
    }
    goToShopping();
    ```

Simplified version:

    ```
    if.isTrueThen(purseStatus == "not found").thenCall(() -> putCashInPocket(cash))
    goToShopping();
    ```

+. If you throw some exception when condition is met otherwise perform operations normally

    ```
    if(breakStatus == "not working") {
        throw new BreakNotWorking();
    }
    keepDriving();
    ```

Simplified version:

    ```
    if.isTrueThen(breakStatus == "not working")).thenThrow(() -> new BreakNotWorking())
    keepDriving();
    ```
