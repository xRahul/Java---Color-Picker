# Java Color Picker

A simple Java Swing application to pick and view colors side-by-side with their information.

## Screenshot

![Screenshot](https://raw.github.com/rahulgr8888/Java---Color-Picker/master/Screens/Screen2.jpg)

## Features

*   Pick colors using the mouse on the main panel.
*   Hue (X-axis) and Saturation (Y-axis) control via mouse movement.
*   Brightness control via mouse wheel.
*   Click to save the current color to a panel and view its details (RGB, Hex).
*   Mac OS Full Screen support.

## How to Run

### Prerequisites

*   Java JDK 8 or higher
*   Maven

### Build and Run

1.  Clone the repository.
2.  Navigate to the project directory.
3.  Run the following command:

    ```bash
    mvn clean package exec:java
    ```

## Development

The source code is located in `src/main/java/com/colorpicker`.
- `Launcher.java`: Entry point.
- `ColorPickerFrame.java`: Main application window and logic.
