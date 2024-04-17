# Fruit Image Classification Android App

This Android application demonstrates image classification using a pre-trained machine learning model to classify fruits into categories such as Apple, Banana, and Orange. The model is integrated into the app using TensorFlow Lite, with optimization for inference using NNAPI (Android Neural Networks API) for improved performance on Android devices.

## Features
- **Image Classification:** Allows users to select an image from the device's gallery and classify it into one of the predefined fruit categories.
- **Real-time Inference:** Performs real-time inference on the device, providing instant results without the need for internet connectivity.
- **Simple User Interface:** Features an intuitive and user-friendly interface for easy interaction.

## Screenshots
<img src="https://github.com/Yogender21505/Classification/assets/104339650/b5861a4a-67b9-4458-b3e1-99c2fc4d295f" alt="Screenshot 4" width="300" height="600">
<img src="https://github.com/Yogender21505/Classification/assets/104339650/8f4f34e3-0ad3-4a4e-87f9-3c3e18cbdf06" alt="Screenshot 3" width="300" height="600">
<img src="https://github.com/Yogender21505/Classification/assets/104339650/0c17f925-ce53-4745-996e-a37cc95f2147" alt="Screenshot 2" width="300" height="600">
<img src="https://github.com/Yogender21505/Classification/assets/104339650/0d57fbbb-5b74-4dbf-8f44-189b147cf242" alt="Screenshot 1" width="300" height="600">

## Installation
To install the app on your Android device, follow these steps:

1. Clone the repository:
   ```bash
   git clone https://github.com/Yogender21505/Classification.git
2. Open the project in Android Studio.
3. Build and run the project on an Android device or emulator.
## Usage

1. Launch the app on your Android device.
2. Tap the "Choose File" button to select an image from your device's gallery.

Once the image is selected, the app will classify it into one of the fruit categories: Apple, Banana, or Orange.

## Technologies Used

- TensorFlow Lite: Used for integrating and running the machine learning model on Android devices.
- Kotlin: The programming language used for developing the Android application.
- Android Jetpack: Utilized for building modern Android apps with ease.
- NNAPI (Android Neural Networks API): Integrated for optimized model inference on Android devices.

## Model

The pre-trained machine learning model used for image classification is available in the model.tflite file. It was optimized for inference on Android devices using NNAPI.

To reproduce the model building process, refer to the provided Google Colab notebook `model_building.ipynb`. Once the model is trained, you can convert it to TensorFlow Lite format (`model.tflite`) and optimize it for NNAPI using the provided instructions.

## Credits

This project was inspired by [source], which provided the initial implementation and model.

## License

This project is licensed under the MIT License.
