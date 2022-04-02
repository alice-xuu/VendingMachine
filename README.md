[![Build Status](https://34.98.85.81/job/Jenkins-vending-machine/badge/icon)](https://34.98.85.81/job/Jenkins-vending-machine/)

# Vending Machine

## Team
- Product Owner: Patrick
- Scrum Master: Jessica
- Core team
  - Joji
  - Alice
  - Anubhav

## How to Run

To build and run the application from this repository execute:

Mac/Linux:
```
./gradlew clean build run
```

Windows:
```
gradlew clean build run
```

To execute the generated jar file:
Make sure you have JDK 11.0.9 or later.

From https://34.98.85.81/job/Jenkins-vending-machine/lastSuccessfulBuild/artifact/ 
click the text under the artifacts labeled `(all files in zip)` to download the `.jar` file
and `credit_cards.json`.
Then extract the zip into a folder and move the `credit_cards.json` and `jar` file into 
the same directory.

Download the javafx library from https://gluonhq.com/products/javafx/.

Assuming the path to javafx installation lib folder is `<path to javafx lib>`

Mac/Linux:
```
java --module-path <path to javafx lib> --add-modules javafx.controls,javafx.media -jar vendingMachine.jar
```

Windows:
```
java --module-path <path to javafx lib> --add-modules javafx.controls,javafx.media -jar .\vendingMachine.jar
```