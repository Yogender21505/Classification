package com.yogender.classification


//import android.graphics.Bitmap
//
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.platform.LocalContext
//import com.yogender.classification.ml.Model
//import org.tensorflow.lite.DataType
//import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
//import java.nio.ByteBuffer
//import java.nio.ByteOrder
//
//object TensorFlowClass {
//
//    val imageSize = 32
//
//    @Composable
//    fun classifyImage(image: Bitmap, callback : (@Composable (fruit : String) -> Unit)) {
//        val model: Model = Model.newInstance(LocalContext.current)
//
//        // Creates inputs for reference.
//        val inputFeature0 =
//            TensorBuffer.createFixedSize(intArrayOf(1, 32, 32, 3), DataType.FLOAT32)
//        val byteBuffer: ByteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3)
//        byteBuffer.order(ByteOrder.nativeOrder())
//        val intValues = IntArray(imageSize * imageSize)
//        image.getPixels(intValues, 0, image.width, 0, 0, image.width, image.height)
//        var pixel = 0
//        //iterate over each pixel and extract R, G, and B values. Add those values individually to the byte buffer.
//        for (i in 0 until imageSize) {
//            for (j in 0 until imageSize) {
//                val `val` = intValues[pixel++] // RGB
//                byteBuffer.putFloat((`val` shr 16 and 0xFF) * (1f / 1))
//                byteBuffer.putFloat((`val` shr 8 and 0xFF) * (1f / 1))
//                byteBuffer.putFloat((`val` and 0xFF) * (1f / 1))
//            }
//        }
//        inputFeature0.loadBuffer(byteBuffer)
//
//        // Runs model inference and gets result.
//        val outputs: Model.Outputs = model.process(inputFeature0)
//        val outputFeature0: TensorBuffer = outputs.getOutputFeature0AsTensorBuffer()
//        val confidences = outputFeature0.floatArray
//        // find the index of the class with the biggest confidence.
//        var maxPos = 0
//        var maxConfidence = 0f
//        for (i in confidences.indices) {
//            if (confidences[i] > maxConfidence) {
//                maxConfidence = confidences[i]
//                maxPos = i
//            }
//        }
//        val classes = arrayOf("Pineapple", "Apple", "Banana", "Orange")
//        callback.invoke(classes[maxPos])
//
//
//        // Releases model resources if no longer used.
//        model.close()
//
//    }
//
//}


import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import org.tensorflow.lite.DataType
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.nnapi.NnApiDelegate
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer
import java.nio.ByteOrder

object APICLASS {

    private const val MODEL_FILE_NAME = "model.tflite"
    private const val NUM_CLASSES = 4
    val imageSize = 32
    init {
        System.loadLibrary("classification")
    }

    fun classifyImage(context: Context, image: Bitmap, callback: (String) -> Unit) {
        // Initialize TensorFlow Lite interpreter with NNAPI delegate if available
        val interpreter = initializeInterpreter(context)

        // Prepare input tensor
        val inputTensor = prepareInputTensor(image)

        // Perform inference
        val outputTensor = TensorBuffer.createFixedSize(intArrayOf(1, NUM_CLASSES), DataType.FLOAT32)
        interpreter.run(inputTensor.buffer, outputTensor.buffer)

        // Process output tensor
        val confidenceArray = outputTensor.floatArray
        var maxIndex = 0
        var maxConfidence = confidenceArray[0]
        for (i in 1 until confidenceArray.size) {
            if (confidenceArray[i] > maxConfidence) {
                maxConfidence = confidenceArray[i]
                maxIndex = i
            }
        }

        // Map index to class name
        val classes = arrayOf("Pineapple", "Apple", "Banana", "Orange")
        val predictedClass = classes[maxIndex]

        // Invoke callback with the predicted class
        callback(predictedClass)

        // Clean up resources
        interpreter.close()
    }

    private fun initializeInterpreter(context: Context): Interpreter {
        val options = Interpreter.Options()

        // Check if NNAPI delegate is available and add it to interpreter options
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val nnApiDelegate = NnApiDelegate()
            options.addDelegate(nnApiDelegate)
        }

        // Initialize TensorFlow Lite interpreter with the specified options
        return Interpreter(loadModelFile(context), options)
    }

    private external fun loadModelFile(assetManager: Any, modelFileName: String): ByteBuffer

    private fun loadModelFile(context: Context): ByteBuffer {
        return loadModelFile(context.assets, MODEL_FILE_NAME)
    }

    private fun prepareInputTensor(image: Bitmap): TensorBuffer {
        val inputTensor = TensorBuffer.createFixedSize(intArrayOf(1, 32, 32, 3), DataType.FLOAT32)
        val byteBuffer = ByteBuffer.allocateDirect(4 * 32 * 32 * 3).order(ByteOrder.nativeOrder())

        // Resize the input image to 32x32 pixels
        val resizedImage = Bitmap.createScaledBitmap(image, 32, 32, true)

        // Extract pixel values and add them to the byte buffer
        for (y in 0 until 32) {
            for (x in 0 until 32) {
                val pixel = resizedImage.getPixel(x, y)
                byteBuffer.putFloat((pixel shr 16 and 0xFF) * (1f / 1))
                byteBuffer.putFloat((pixel shr 8 and 0xFF) * (1f / 1))
                byteBuffer.putFloat((pixel and 0xFF) * (1f / 1))
            }
        }

        inputTensor.loadBuffer(byteBuffer)
        return inputTensor
    }
}
//fun classifyImage(image: Bitmap, callback : (@Composable (fruit : String) -> Unit)) {
//    val model: Model = Model.newInstance(LocalContext.current)
//
//    // Creates inputs for reference.
//    val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 32, 32, 3), DataType.FLOAT32)
//    val byteBuffer: ByteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3)
//    byteBuffer.order(ByteOrder.nativeOrder())
//    val intValues = IntArray(imageSize * imageSize)
//    image.getPixels(intValues, 0, image.width, 0, 0, image.width, image.height)
//    var pixel = 0
//    //iterate over each pixel and extract R, G, and B values. Add those values individually to the byte buffer.
//    for (i in 0 until imageSize) {
//        for (j in 0 until imageSize) {
//            val `val` = intValues[pixel++] // RGB
//            byteBuffer.putFloat((`val` shr 16 and 0xFF) * (1f / 1))
//            byteBuffer.putFloat((`val` shr 8 and 0xFF) * (1f / 1))
//            byteBuffer.putFloat((`val` and 0xFF) * (1f / 1))
//        }
//    }
//    inputFeature0.loadBuffer(byteBuffer)
//
//    // Runs model inference and gets result.
//    val outputs: Model.Outputs = model.process(inputFeature0)
//    val outputFeature0: TensorBuffer = outputs.getOutputFeature0AsTensorBuffer()
//    val confidences = outputFeature0.floatArray
//    // find the index of the class with the biggest confidence.
//    var maxPos = 0
//    var maxConfidence = 0f
//    for (i in confidences.indices) {
//        if (confidences[i] > maxConfidence) {
//            maxConfidence = confidences[i]
//            maxPos = i
//        }
//    }
//    val classes = arrayOf("Pineapple", "Apple", "Banana", "Orange")
//    callback.invoke(classes[maxPos])
//
//
//    // Releases model resources if no longer used.
//    model.close()
//
//}