// OpenCVApplication.cpp : Defines the entry point for the console application.
//


#include "common.h"
#include <random>

void testOpenImage() {
    char fname[MAX_PATH];
    while (openFileDlg(fname)) {
        Mat src;
        src = imread(fname);
        imshow("image", src);
        waitKey();
    }
}

void testOpenImagesFld() {
    char folderName[MAX_PATH];
    if (openFolderDlg(folderName) == 0)
        return;
    char fname[MAX_PATH];
    FileGetter fg(folderName, "bmp");
    while (fg.getNextAbsFile(fname)) {
        Mat src;
        src = imread(fname);
        imshow(fg.getFoundFileName(), src);
        if (waitKey() == 27) //ESC pressed
            break;
    }
}

void testImageOpenAndSave() {
    Mat src, dst;

    src = imread("Images/Lena_24bits.bmp", IMREAD_COLOR);    // Read the image

    if (!src.data)    // Check for invalid input
    {
        printf("Could not open or find the image\n");
        return;
    }

    // Get the image resolution
    Size src_size = Size(src.cols, src.rows);

    // Display window
    const char *WIN_SRC = "Src"; //window for the source image
    namedWindow(WIN_SRC, WINDOW_AUTOSIZE);
    moveWindow(WIN_SRC, 0, 0);

    const char *WIN_DST = "Dst"; //window for the destination (processed) image
    namedWindow(WIN_DST, WINDOW_AUTOSIZE);
    moveWindow(WIN_DST, src_size.width + 10, 0);

    cvtColor(src, dst, COLOR_BGR2GRAY); //converts the source image to a grayscale one

    imwrite("Images/Lena_24bits_gray.bmp", dst); //writes the destination to file

    imshow(WIN_SRC, src);
    imshow(WIN_DST, dst);

    printf("Press any key to continue ...\n");
    waitKey(0);
}

void testNegativeImage() {
    char fname[MAX_PATH];
    while (openFileDlg(fname)) {
        double t = (double) getTickCount(); // Get the current time [s]

        Mat src = imread(fname, IMREAD_GRAYSCALE);
        int height = src.rows;
        int width = src.cols;
        Mat dst = Mat(height, width, CV_8UC1);
        // Asa se acceseaaza pixelii individuali pt. o imagine cu 8 biti/pixel
        // Varianta ineficienta (lenta)
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                uchar val = src.at<uchar>(i, j);
                uchar neg = 255 - val;
                dst.at<uchar>(i, j) = neg;
            }
        }

        // Get the current time again and compute the time difference [s]
        t = ((double) getTickCount() - t) / getTickFrequency();
        // Print (in the console window) the processing time in [ms]
        printf("Time = %.3f [ms]\n", t * 1000);

        imshow("input image", src);
        imshow("negative image", dst);
        waitKey();
    }
}

void testParcurgereSimplaDiblookStyle() {
    char fname[MAX_PATH];
    while (openFileDlg(fname)) {
        Mat src = imread(fname, IMREAD_GRAYSCALE);
        int height = src.rows;
        int width = src.cols;
        Mat dst = src.clone();

        double t = (double) getTickCount(); // Get the current time [s]

        // the fastest approach using the ìdiblook styleî
        uchar *lpSrc = src.data;
        uchar *lpDst = dst.data;
        int w = (int) src.step; // no dword alignment is done !!!
        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++) {
                uchar val = lpSrc[i * w + j];
                lpDst[i * w + j] = 255 - val;
            }

        // Get the current time again and compute the time difference [s]
        t = ((double) getTickCount() - t) / getTickFrequency();
        // Print (in the console window) the processing time in [ms]
        printf("Time = %.3f [ms]\n", t * 1000);

        imshow("input image", src);
        imshow("negative image", dst);
        waitKey();
    }
}

void testColor2Gray() {
    char fname[MAX_PATH];
    while (openFileDlg(fname)) {
        Mat src = imread(fname);

        int height = src.rows;
        int width = src.cols;

        Mat dst = Mat(height, width, CV_8UC1);

        // Asa se acceseaaza pixelii individuali pt. o imagine RGB 24 biti/pixel
        // Varianta ineficienta (lenta)
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Vec3b v3 = src.at<Vec3b>(i, j);
                uchar b = v3[0];
                uchar g = v3[1];
                uchar r = v3[2];
                dst.at<uchar>(i, j) = (r + g + b) / 3;
            }
        }

        imshow("input image", src);
        imshow("gray image", dst);
        waitKey();
    }
}

void testBGR2HSV() {
    char fname[MAX_PATH];
    while (openFileDlg(fname)) {
        Mat src = imread(fname);
        int height = src.rows;
        int width = src.cols;

        // Componentele d eculoare ale modelului HSV
        Mat H = Mat(height, width, CV_8UC1);
        Mat S = Mat(height, width, CV_8UC1);
        Mat V = Mat(height, width, CV_8UC1);

        // definire pointeri la matricele (8 biti/pixeli) folosite la afisarea componentelor individuale H,S,V
        uchar *lpH = H.data;
        uchar *lpS = S.data;
        uchar *lpV = V.data;

        Mat hsvImg;
        cvtColor(src, hsvImg, COLOR_BGR2HSV);

        // definire pointer la matricea (24 biti/pixeli) a imaginii HSV
        uchar *hsvDataPtr = hsvImg.data;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int hi = i * width * 3 + j * 3;
                int gi = i * width + j;

                lpH[gi] = hsvDataPtr[hi] * 510 / 360;        // lpH = 0 .. 255
                lpS[gi] = hsvDataPtr[hi + 1];            // lpS = 0 .. 255
                lpV[gi] = hsvDataPtr[hi + 2];            // lpV = 0 .. 255
            }
        }

        imshow("input image", src);
        imshow("H", H);
        imshow("S", S);
        imshow("V", V);

        waitKey();
    }
}

void testResize() {
    char fname[MAX_PATH];
    while (openFileDlg(fname)) {
        Mat src;
        src = imread(fname);
        Mat dst1, dst2;
        //without interpolation
        resizeImg(src, dst1, 320, false);
        //with interpolation
        resizeImg(src, dst2, 320, true);
        imshow("input image", src);
        imshow("resized image (without interpolation)", dst1);
        imshow("resized image (with interpolation)", dst2);
        waitKey();
    }
}

void testCanny() {
    char fname[MAX_PATH];
    while (openFileDlg(fname)) {
        Mat src, dst, gauss;
        src = imread(fname, IMREAD_GRAYSCALE);
        double k = 0.4;
        int pH = 50;
        int pL = (int) k * pH;
        GaussianBlur(src, gauss, Size(5, 5), 0.8, 0.8);
        Canny(gauss, dst, pL, pH, 3);
        imshow("input image", src);
        imshow("canny", dst);
        waitKey();
    }
}

void testVideoSequence() {
    VideoCapture cap("Videos/rubic.avi"); // off-line video from file
    //VideoCapture cap(0);	// live video from web cam
    if (!cap.isOpened()) {
        printf("Cannot open video capture device.\n");
        waitKey(0);
        return;
    }

    Mat edges;
    Mat frame;
    char c;

    while (cap.read(frame)) {
        Mat grayFrame;
        cvtColor(frame, grayFrame, COLOR_BGR2GRAY);
        Canny(grayFrame, edges, 40, 100, 3);
        imshow("source", frame);
        imshow("gray", grayFrame);
        imshow("edges", edges);
        c = waitKey(0);  // waits a key press to advance to the next frame
        if (c == 27) {
            // press ESC to exit
            printf("ESC pressed - capture finished\n");
            break;  //ESC pressed
        };
    }
}


void testSnap() {
    VideoCapture cap(0); // open the deafult camera (i.e. the built in web cam)
    if (!cap.isOpened()) // openenig the video device failed
    {
        printf("Cannot open video capture device.\n");
        return;
    }

    Mat frame;
    char numberStr[256];
    char fileName[256];

    // video resolution
    Size capS = Size((int) cap.get(CAP_PROP_FRAME_WIDTH),
                     (int) cap.get(CAP_PROP_FRAME_HEIGHT));

    // Display window
    const char *WIN_SRC = "Src"; //window for the source frame
    namedWindow(WIN_SRC, WINDOW_AUTOSIZE);
    moveWindow(WIN_SRC, 0, 0);

    const char *WIN_DST = "Snapped"; //window for showing the snapped frame
    namedWindow(WIN_DST, WINDOW_AUTOSIZE);
    moveWindow(WIN_DST, capS.width + 10, 0);

    char c;
    int frameNum = -1;
    int frameCount = 0;

    for (;;) {
        cap >> frame; // get a new frame from camera
        if (frame.empty()) {
            printf("End of the video file\n");
            break;
        }

        ++frameNum;

        imshow(WIN_SRC, frame);

        c = waitKey(10);  // waits a key press to advance to the next frame
        if (c == 27) {
            // press ESC to exit
            printf("ESC pressed - capture finished");
            break;  //ESC pressed
        }
        if (c == 115) { //'s' pressed - snapp the image to a file
            frameCount++;
            fileName[0] = NULL;
            sprintf(numberStr, "%d", frameCount);
            strcat(fileName, "Images/A");
            strcat(fileName, numberStr);
            strcat(fileName, ".bmp");
            bool bSuccess = imwrite(fileName, frame);
            if (!bSuccess) {
                printf("Error writing the snapped image\n");
            } else
                imshow(WIN_DST, frame);
        }
    }

}

void MyCallBackFunc(int event, int x, int y, int flags, void *param) {
    //More examples: http://opencvexamples.blogspot.com/2014/01/detect-mouse-clicks-and-moves-on-image.html
    Mat *src = (Mat *) param;
    if (event == EVENT_LBUTTONDOWN) {
        printf("Pos(x,y): %d,%d  Color(RGB): %d,%d,%d\n",
               x, y,
               (int) (*src).at<Vec3b>(y, x)[2],
               (int) (*src).at<Vec3b>(y, x)[1],
               (int) (*src).at<Vec3b>(y, x)[0]);
    }
}

void testMouseClick() {
    Mat src;
    // Read image from file
    char fname[MAX_PATH];
    while (openFileDlg(fname)) {
        src = imread(fname);
        //Create a window
        namedWindow("My Window", 1);

        //set the callback function for any mouse event
        setMouseCallback("My Window", MyCallBackFunc, &src);

        //show the image
        imshow("My Window", src);

        // Wait until user press some key
        waitKey(0);

    }
}

/* Histogram display function - display a histogram using bars (simlilar to L3 / PI)
Input:
name - destination (output) window name
hist - pointer to the vector containing the histogram values
hist_cols - no. of bins (elements) in the histogram = histogram image width
hist_height - height of the histogram image
Call example:
showHistogram ("MyHist", hist_dir, 255, 200);
*/

//----------------------------------------------------LABORATOR1-----------------------------------------------------------
//EX3
void brightnessAditive(int const additive) {
    char fname[MAX_PATH];
    while (openFileDlg(fname)) {
        double t = (double) getTickCount(); // Get the current time [s]

        Mat src = imread(fname, CV_LOAD_IMAGE_GRAYSCALE);
        int height = src.rows;
        int width = src.cols;
        Mat dst = Mat(height, width, CV_8UC1);
        // Asa se acceseaaza pixelii individuali pt. o imagine cu 8 biti/pixel
        // Varianta ineficienta (lenta)
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                uchar val = src.at<uchar>(i, j); //accesa pixelul de la coordonatele (i,j)
                int newVal = val + additive;
                int neg = min(newVal, 255);
                uchar neg2 = max(neg, 0);
                dst.at<uchar>(i, j) = neg2;
            }
        }

        // Get the current time again and compute the time difference [s]
        t = ((double) getTickCount() - t) / getTickFrequency();
        // Print (in the console window) the processing time in [ms]
        printf("Time = %.3f [ms]\n", t * 1000);

        imshow("input image", src);
        imshow("negative image", dst);

        waitKey();
    }
}

//EX4
void brightnessMultiplicative(int const multiplicative) {
    char fname[MAX_PATH];
    while (openFileDlg(fname)) {
        double t = (double) getTickCount(); // Get the current time [s]

        Mat src = imread(fname, CV_LOAD_IMAGE_GRAYSCALE);
        int height = src.rows;
        int width = src.cols;
        Mat dst = Mat(height, width, CV_8UC1);
        // Asa se acceseaaza pixelii individuali pt. o imagine cu 8 biti/pixel
        // Varianta ineficienta (lenta)
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                uchar val = src.at<uchar>(i, j);
                int newVal = val * multiplicative;
                int neg = min(newVal, 255);
                uchar neg2 = max(neg, 0);
                dst.at<uchar>(i, j) = neg2;
            }
        }

        // Get the current time again and compute the time difference [s]
        t = ((double) getTickCount() - t) / getTickFrequency();
        // Print (in the console window) the processing time in [ms]
        printf("Time = %.3f [ms]\n", t * 1000);

        imshow("input image", src);
        imshow("negative image", dst);
        waitKey();
    }
}

//EX5
void createMatrix_256() {
    Mat matrix = Mat(256, 256, CV_8UC3); //imaginea color
    Vec3b white = Vec3b(255, 255, 255);  //culorile
    Vec3b red = Vec3b(255, 0, 0);
    Vec3b blue = Vec3b(0, 0, 255);
    Vec3b green = Vec3b(0, 255, 0);

    for (int i = 0; i < 255; i++) {
        for (int j = 0; j < 255; j++) {
            if (i < 128 && j < 128) {
                matrix.at<Vec3b>(i, j) = white;
            } else if (i < 128 && j > 127) {
                matrix.at<Vec3b>(i, j) = red;
            } else if (i > 127 && j < 128) {
                matrix.at<Vec3b>(i, j) = blue;
            } else {
                matrix.at<Vec3b>(i, j) = green;
            }
        }
    }
    imshow("result image", matrix);
    waitKey();
}

//EX6
void createMatrixFloat() {
    float vals[9] = {1, 2, 3, 4, 5, 6, 7, 8, 12};
    Mat matrix = Mat(3, 3, CV_32FC1, vals);
    Mat matrixDst = Mat(3, 3, CV_32FC1);    //nu e nevoie de declarare matrice
    matrixDst = matrix.inv();


    for (int i = 0; i < 3; i++) {
        for (int j = 0; j < 3; j++) {
            printf("%.3f ", matrixDst.at<float>(i, j));
        }
        printf("\n");
    }
    waitKey();
    waitKey(10000);
}


//----------------------------------------------------LABORATOR2-----------------------------------------------------------

//EX1

void copyRGB() {
    char fname[MAX_PATH];
    while (openFileDlg(fname)) {
        double t = (double) getTickCount(); // Get the current time [s]

        Mat src = imread(fname, CV_LOAD_IMAGE_COLOR);
        int height = src.rows;
        int width = src.cols;
        Mat dstR = Mat(height, width, CV_8UC1);
        Mat dstG = Mat(height, width, CV_8UC1);
        Mat dstB = Mat(height, width, CV_8UC1);
        // Asa se acceseaaza pixelii individuali pt. o imagine cu 8 biti/pixel
        // Varianta ineficienta (lenta)
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Vec3b val = src.at<Vec3b>(i, j); //accesa pixelul de la coordonatele (i,j)
                dstB.at<uchar>(i, j) = val[0];
                dstG.at<uchar>(i, j) = val[1];
                dstR.at<uchar>(i, j) = val[2];
            }
        }

        // Get the current time again and compute the time difference [s]
        t = ((double) getTickCount() - t) / getTickFrequency();
        // Print (in the console window) the processing time in [ms]
        printf("Time = %.3f [ms]\n", t * 1000);

        imshow("Blue", dstB);
        imshow("Green", dstG);
        imshow("Red", dstR);
        waitKey();
    }

}

//EX2

void convertFromRGBtoGray() {
    char fname[MAX_PATH];
    while (openFileDlg(fname)) {
        double t = (double) getTickCount(); // Get the current time [s]

        Mat src = imread(fname, CV_LOAD_IMAGE_COLOR);
        int height = src.rows;
        int width = src.cols;
        Mat dst = Mat(height, width, CV_8UC1);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Vec3b val = src.at<Vec3b>(i, j); //accesa pixelul de la coordonatele (i,j)
                uchar valFinal = (val[0] + val[1] + val[2]) / 3; //2.3
                dst.at<uchar>(i, j) = valFinal;
            }
        }


        // Get the current time again and compute the time difference [s]
        t = ((double) getTickCount() - t) / getTickFrequency();
        // Print (in the console window) the processing time in [ms]
        printf("Time = %.3f [ms]\n", t * 1000);

        imshow("input image", src);
        imshow("result image", dst);

        waitKey();
    }

}

//EX3

void convertFromGrayToWB(int threshold) {//imagine binara -binarizare cu prag(thresholding)
    char fname[MAX_PATH];
    while (openFileDlg(fname)) {
        double t = (double) getTickCount(); // Get the current time [s]

        Mat src = imread(fname, CV_LOAD_IMAGE_GRAYSCALE);
        int height = src.rows;
        int width = src.cols;

        Mat dst = Mat(height, width, CV_8UC1);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                uchar valFinal;
                uchar val = src.at<uchar>(i, j);
                if (val < threshold) {
                    dst.at<uchar>(i, j) = 0;//black
                } else if (val >= threshold) {
                    dst.at<uchar>(i, j) = 255;
                }

            }
        }
        // Get the current time again and compute the time difference [s]
        t = ((double) getTickCount() - t) / getTickFrequency();
        // Print (in the console window) the processing time in [ms]
        printf("Time = %.3f [ms]\n", t * 1000);

        // imshow("input image", src);
        imshow("result image", dst);

        waitKey();
    }

}

//EX4

void convertFromRGBToHSV() {
    char fname[MAX_PATH];
    uchar R, G, B;
    float r, g, b, M, m, C, V, S, H, H_norm, S_norm, V_norm;
    while (openFileDlg(fname)) {
        double t = (double) getTickCount(); // Get the current time [s]

        Mat src = imread(fname, CV_LOAD_IMAGE_COLOR);
        int height = src.rows;
        int width = src.cols;

        Mat dstH = Mat(height, width, CV_8UC1);
        Mat dstS = Mat(height, width, CV_8UC1);
        Mat dstV = Mat(height, width, CV_8UC1);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Vec3b val = src.at<Vec3b>(i, j);
                B = val[0];
                G = val[1];
                R = val[2];
                r = (float) R / 255; //conversie pt ca R,G,B de tip uchar
                g = (float) G / 255;
                b = (float) B / 255;
                M = max(r, max(g, b));
                m = min(r, min(g, b));
                C = M - m;
                V = M;  //Value
                if (V != 0) {   //Saturatie
                    S = C / V;
                } else {
                    S = 0; //negru  --imagine grayscale
                }
                if (C != 0) {
                    if (M == r) H = 60 * (g - b) / C;
                    if (M == g) H = 120 + 60 * (b - r) / C;
                    if (M == b) H = 240 + 60 * (r - g) / C;
                } else {
                    H = 0; //grayscale
                }
                if (H < 0) {
                    H = H + 360;    //H=0..360 , S=0..1, V=0..1
                }
                //normalizare  in intervalul 0..255 pt a fi o imagine cu 8 biti/pixel
                H_norm = H * 255 / 360;
                S_norm = S * 255;
                V_norm = V * 255;

                dstH.at<uchar>(i, j) = (uchar) H_norm;
                dstS.at<uchar>(i, j) = (uchar) S_norm;
                dstV.at<uchar>(i, j) = (uchar) V_norm;
            }
        }
        // Get the current time again and compute the time difference [s]
        t = ((double) getTickCount() - t) / getTickFrequency();
        // Print (in the console window) the processing time in [ms]
        printf("Time = %.3f [ms]\n", t * 1000);

        imshow("input image", src);
        imshow("result image H", dstH);
        imshow("result image S", dstS);
        imshow("result image V", dstV);

        waitKey();
    }

}
//EX5

bool isInside(Mat img, int i, int j) {
    int rows = img.rows;
    int cols = img.cols;
    if (i < rows && i >= 0 && j < cols && j >= 0) {
        return true;

    }
    return false;
    waitKey();
}



//----------------------------------------------------LABORATOR3-----------------------------------------------------------

//Lab3
void showHistogram(const std::string &name, const int *hist, const int hist_cols, const int hist_height) {
    Mat imgHist(hist_height, hist_cols, CV_8UC3, CV_RGB(255, 255, 255));

    int max_hist = 0;
    for (int i = 0; i < hist_cols; i++)
        if (hist[i] > max_hist)
            max_hist = hist[i];
    double scale = 1.0;
    scale = (double) hist_height / max_hist;
    int baseline = hist_height - 1;
    for (int x = 0; x < hist_cols; x++) {
        Point p1 = Point(x, baseline);
        Point p2 = Point(x, baseline - cvRound(hist[x] * scale));
        line(imgHist, p1, p2, CV_RGB(255, 0, 255)); // histogram bins
    }
    imshow(name, imgHist);
}

int *computeHistogram(Mat img) {
    int *histogram = new int[256];

    for (int i = 0; i < 256; i++) {
        histogram[i] = 0;
    }

    for (int i = 0; i < img.rows; i++) {
        for (int j = 0; j < img.cols; j++) {


            histogram[img.at<uchar>(i, j)] += 1;
        }
    }
    return histogram;
}


void printVectorHistogram() {
    char fname[MAX_PATH];
    while (openFileDlg(fname)) {


        Mat src = imread(fname, CV_LOAD_IMAGE_GRAYSCALE);
        int *h = computeHistogram(src);
        for (int L = 0; L < 256; L++) {
            printf("[%d]-> %d \n", L, h[L]);
        }

        waitKey();
    }
}

float *computeFDP(const int *histogram, Mat img) {
    float *fdp = new float[256];
    auto M = img.cols * img.rows;

    for (int i = 0; i < 256; i++) {
        fdp[i] = 0;
    }
    for (int i = 0; i < 256; i++) {
        fdp[i] = (float) histogram[i] / M;
    }
    return fdp;
}

void printVectorFDP() {
    char fname[MAX_PATH];
    while (openFileDlg(fname)) {


        Mat src = imread(fname, CV_LOAD_IMAGE_GRAYSCALE);
        int *h = computeHistogram(src);
        float *fdp = computeFDP(h, src);
        for (int L = 0; L < 256; L++) {
            printf("[%d]-> %f \n", L, fdp[L]);
        }

        waitKey();
    }
}

void printHistogram() {
    char fname[MAX_PATH];
    while (openFileDlg(fname)) {


        Mat src = imread(fname, CV_LOAD_IMAGE_GRAYSCALE);
        int *h = computeHistogram(src);
        showHistogram("Histogram", h, 256, 256);

        waitKey();
    }

}


int *computeHistogramForGivenBins(const int *histogram, int m) {
    int *newHisto = new int[200];
    float rescale = 256 / m;
    int newI;

    for (int i = 0; i < 256; i++) {
        newHisto[i] = 0;
    }

    for (int i = 0; i < 256; i++) {
        newI = floor(i / rescale); //sa dea intreg
        newHisto[newI] += histogram[i];
    }

    return newHisto;
}

void printVectorNewHistogram(int m) {
    char fname[MAX_PATH];
    while (openFileDlg(fname)) {


        Mat src = imread(fname, CV_LOAD_IMAGE_GRAYSCALE);
        int *h = computeHistogram(src);
        int *newH = computeHistogramForGivenBins(h, m);
        for (int L = 0; L < 256; L++) {
            printf("[%d]-> %d \n", L, newH[L]);
        }
        showHistogram("histogram_image", newH, m, 256);

        waitKey();
    }
}

int checkBoundaries(int val) {
    return max(0, min(255, val));
}

std::vector<int> computeLocalMaximas(Mat &original) {
    int *histogram = computeHistogram(original);
    float *pdf = computeFDP(histogram, original);

    std::vector<int> histoMaximas;
    int WH = 5;
    float window_width = 2 * WH + 1, TH = 0.0003;

    for (int k = 0 + WH; k < 255 - WH; k++) {
        float averageV = 0;
        bool ok = true;

        for (int j = k - WH; j <= k + WH; j++) {

            averageV += pdf[j];

            if (pdf[k] < pdf[j]) {
                ok = false;
            }
        }
        averageV /= window_width;
        if (pdf[k] > (averageV + TH) && ok) {

            histoMaximas.push_back(k);
        }
    }
    //insert 0 at the beginning of the maximas list and 255 at the end
    histoMaximas.insert(histoMaximas.begin(), 0);
    histoMaximas.insert(histoMaximas.begin() + histoMaximas.size(), 255);

    return histoMaximas;
}

void multilevelThresholding() {
    char fname[MAX_PATH];
    while (openFileDlg(fname)) {
        Mat img = imread(fname, CV_LOAD_IMAGE_GRAYSCALE);
        std::vector<int> histoMaximas = computeLocalMaximas(img);
        Mat threshholdedImage = Mat(img.rows, img.cols, CV_8UC1);

        int min, pixel;

        for (int i = 0; i < img.rows; i++) {
            for (int j = 0; j < img.cols; j++) {
                pixel = img.at<uchar>(i, j);
                min = 0;
                for (int localMaxima: histoMaximas) {

                    if (abs(pixel - min) > abs(pixel - localMaxima)) {
                        min = localMaxima;
                    }
                }

                threshholdedImage.at<uchar>(i, j) = min;
            }
        }
        imshow("image", threshholdedImage);
        waitKey();
    }

}


void floyd_steinberg() {
    char fname[MAX_PATH];
    while (openFileDlg(fname)) {
        Mat original = imread(fname, CV_LOAD_IMAGE_GRAYSCALE);

        std::vector<int> histoMaximas = computeLocalMaximas(original);
        int min, pixel, newPixel, error;

        for (int i = 0; i < original.rows; i++) {
            for (int j = 0; j < original.cols; j++) {
                pixel = original.at<uchar>(i, j);
                min = 0;
                for (int localMaxima: histoMaximas) {
                    if (abs(pixel - min) > abs(pixel - localMaxima)) {
                        min = localMaxima;
                    }
                }
                newPixel = min;
                original.at<uchar>(i, j) = newPixel;
                error = pixel - newPixel;

                if (isInside(original, i, j + 1)) {
                    original.at<uchar>(i, j + 1) = checkBoundaries(original.at<uchar>(i, j + 1) + 7 * error / 16);
                }
                if (isInside(original, i + 1, j - 1)) {
                    original.at<uchar>(i + 1, j - 1) = checkBoundaries(
                            original.at<uchar>(i + 1, j - 1) + 3 * error / 16);
                }
                if (isInside(original, i + 1, j)) {
                    original.at<uchar>(i + 1, j) = checkBoundaries(original.at<uchar>(i + 1, j) + 5 * error / 16);
                }
                if (isInside(original, i + 1, j + 1)) {
                    original.at<uchar>(i + 1, j + 1) = checkBoundaries(
                            original.at<uchar>(i + 1, j + 1) + 1 * error / 16);
                }
            }
        }
        imshow("image", original);
        waitKey();
    }


}

void multilevelThresholdingHSV() {
    int WH = 5;
    float TH = 0.0003f;
    char fname[MAX_PATH];
    uchar R, G, B;
    float r, g, b, M, m, C, V, S, H, H_norm, S_norm, V_norm;
    int mmin, pixel, newPixel, error;
    while (openFileDlg(fname)) {
        Mat src = imread(fname, CV_LOAD_IMAGE_COLOR);
        int height = src.rows;
        int width = src.cols;

        Mat dstH = Mat(height, width, CV_8UC1);
        Mat dstS = Mat(height, width, CV_8UC1);
        Mat dstV = Mat(height, width, CV_8UC1);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Vec3b val = src.at<Vec3b>(i, j);
                B = val[0];
                G = val[1];
                R = val[2];
                r = (float) R / 255; //conversie pt ca R,G,B de tip uchar
                g = (float) G / 255;
                b = (float) B / 255;
                M = max(r, max(g, b));
                m = min(r, min(g, b));
                C = M - m;
                V = M;  //Value
                if (V != 0) {   //Saturatie
                    S = C / V;
                } else {
                    S = 0; //negru  --imagine grayscale
                }
                if (C != 0) {
                    if (M == r) H = 60 * (g - b) / C;
                    if (M == g) H = 120 + 60 * (b - r) / C;
                    if (M == b) H = 240 + 60 * (r - g) / C;
                } else {
                    H = 0; //grayscale
                }
                if (H < 0) {
                    H = H + 360;    //H=0..360 , S=0..1, V=0..1
                }

                H_norm = H * 255 / 360;
                S_norm = S * 255;
                V_norm = V * 255;

                dstH.at<uchar>(i, j) = (uchar) H_norm;
                dstS.at<uchar>(i, j) = (uchar) S_norm;
                dstV.at<uchar>(i, j) = (uchar) V_norm;
            }
        }
        std::vector<int> histoMaximas = computeLocalMaximas(dstH);
        for (int i = 0; i < dstH.rows; i++) {
            for (int j = 0; j < dstH.cols; j++) {
                pixel = dstH.at<uchar>(i, j);
                mmin = 0;
                for (int localMaxima: histoMaximas) {
                    if (abs(pixel - mmin) > abs(pixel - localMaxima)) {
                        mmin = localMaxima;
                    }
                }
                newPixel = mmin;
                dstH.at<uchar>(i, j) = newPixel;
                error = pixel - newPixel;

                if (isInside(dstH, i, j + 1)) {
                    dstH.at<uchar>(i, j + 1) = checkBoundaries(dstH.at<uchar>(i, j + 1) + 7 * error / 16);
                }
                if (isInside(dstH, i + 1, j - 1)) {
                    dstH.at<uchar>(i + 1, j - 1) = checkBoundaries(dstH.at<uchar>(i + 1, j - 1) + 3 * error / 16);
                }
                if (isInside(dstH, i + 1, j)) {
                    dstH.at<uchar>(i + 1, j) = checkBoundaries(dstH.at<uchar>(i + 1, j) + 5 * error / 16);
                }
                if (isInside(dstH, i + 1, j + 1)) {
                    dstH.at<uchar>(i + 1, j + 1) = checkBoundaries(dstH.at<uchar>(i + 1, j + 1) + 1 * error / 16);
                }
            }
        }
        imshow("input image", src);
        imshow("result image H", dstH);
        imshow("result image S", dstS);
        imshow("result image V", dstV);
        waitKey();
    }


}


double calcAxaAlungire(Mat img, Vec3b pixel, int xc, int yc) {
    int numarator = 0, numitor = 0;


    for (int i = 0; i < img.rows; i++) {
        for (int j = 0; j < img.cols; j++) {
            if (pixel == img.at<Vec3b>(i, j)) {
                numarator += 2 * (i - yc) * (j - xc);//I(r,c) e 1
                numitor += pow((j - xc), 2) - pow((i - yc), 2);
            }
        }
    }
    double axa = (atan2(2 * numarator, (double) numitor)) / 2;


    return axa;


}

int calcAria(Mat img, Vec3b pixel) {
    int area = 0;

    for (int i = 0; i < img.rows; i++) {
        for (int j = 0; j < img.cols; j++) {
            if (pixel == img.at<Vec3b>(i, j)) {
                area++;
            }
        }
    }
    return area;
}

bool isContour(Mat img, Vec3b pixel, int i, int j) {
    if (isInside(img, i + 1, j + 1) && img.at<Vec3b>(i + 1, j + 1) != pixel)
        return true;
    if (isInside(img, i, j + 1) && img.at<Vec3b>(i, j + 1) != pixel)
        return true;
    if (isInside(img, i - 1, j + 1) && img.at<Vec3b>(i - 1, j + 1) != pixel)
        return true;
    if (isInside(img, i - 1, j) && img.at<Vec3b>(i - 1, j) != pixel)
        return true;
    if (isInside(img, i - 1, j - 1) && img.at<Vec3b>(i - 1, j - 1) != pixel)
        return true;
    if (isInside(img, i, j - 1) && img.at<Vec3b>(i, j - 1) != pixel)
        return true;
    if (isInside(img, i + 1, j - 1) && img.at<Vec3b>(i + 1, j - 1) != pixel)
        return true;
    if (isInside(img, i + 1, j) && img.at<Vec3b>(i + 1, j) != pixel)
        return true;
    return false;

}

float calcFactorDeSubtiere(int aria, int perimetru) {
    float thickness = 4.0f * PI * aria / (perimetru * perimetru);
    return thickness;
}

float calcFactorDeAspect(Mat img, Vec3b pixel) {
    int maxr = -1, maxc = -1;
    int minr = img.rows, minc = img.cols;
    for (int i = 0; i < img.rows; i++) {
        for (int j = 0; j < img.cols; j++) {
            if (img.at<Vec3b>(i, j) == pixel) {
                if (j > maxc)
                    maxc = j;
                if (j < minc)
                    minc = j;
                if (i > maxr)
                    maxr = i;
                if (i < minr)
                    minr = i;

            }
        }
    }
    return (maxc - minc + 1.0f) / (maxr - minr + 1.0f);
}


void MyCallBackFunc2(int event, int x, int y, int flags, void *param) {
    Mat *src1 = (Mat *) param;
    Mat src = (*src1);
    if (event == CV_EVENT_LBUTTONDOWN) {
        printf("Pos(x,y): %d,%d  Color(RGB): %d,%d,%d\n", x, y, (int) (src).at<Vec3b>(y, x)[2],
               (int) (src).at<Vec3b>(y, x)[1], (int) (src).at<Vec3b>(y, x)[0]);
        Vec3b color = src.at<Vec3b>(y, x);
        Mat dst = src.clone();
        Mat proj = Mat::zeros(src.size(), CV_8UC1);
        int height = src.rows;
        int width = src.cols;
        int *h = (int *) calloc(sizeof(int), height);
        int *w = (int *) calloc(sizeof(int), width);


        int aria = calcAria(src, color);
        int xc = 0;
        int yc = 0;
        int perimetru = 0;
        int i, j;


        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Vec3b crt_color = src.at<Vec3b>(i, j);
                if (crt_color == color) {

                    xc += j;
                    yc += i;
                    if (isContour(src, color, i, j)) {

                        perimetru++;
                        dst.at<Vec3b>(i, j) = Vec3b(0, 0, 0);
                    }

                    h[i]++;
                    w[j]++;
                }
            }
        }
        float ratio = calcFactorDeAspect(src, color);
        float thinness = calcFactorDeSubtiere(aria, perimetru);

        int xcnorm = xc / aria;
        int ycnorm = yc / aria;

        double axa = calcAxaAlungire(src, color, xcnorm, ycnorm);
        int grade = axa * 180 / PI;


        printf("Aria este %d\n", aria);
        printf("Centrul de masa este (%d, %d)\n", xcnorm, ycnorm);
        printf("Axa de alungire este   %.2f grade\n", axa);
        printf("Perimetru este   %d\n", perimetru);
        printf("Factorul de subtiere este %.2f\n", thinness);
        printf("Elongatia este  %.2f\n", ratio);


        drawMarker(dst, Point(xcnorm, ycnorm), Scalar(0, 0, 0), MARKER_CROSS, 10, thinness);
        int delta = 40;
        Point P1 = Point(xcnorm - delta, ycnorm - (int) (delta * tan(axa)));
        Point P2 = Point(xcnorm + delta, ycnorm + (int) (delta * tan(axa)));
        line(dst, P1, P2, Scalar(0, 0, 0), thinness, 8);


        Mat projR = Mat::zeros(src.size(), CV_8UC1);
        Mat projC = Mat::zeros(src.size(), CV_8UC1);


        for (int i = 0; i < height; i++) {
            for (int j = 0; j < h[i]; j++) {
                projR.at<uchar>(i, j) = 255;
            }
        }
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < w[i]; j++) {
                projC.at<uchar>(j, i) = 255;
            }
        }
        imshow("CMA", dst);
        imshow("projection row", projR);
        imshow("projection col", projC);
    }
}

void testMouseClick3() {
    Mat src;
    // Read image from file
    char fname[MAX_PATH];
    while (openFileDlg(fname)) {
        src = imread(fname);
        //Create a window
        namedWindow("My Window", 1);

        //set the callback function for any mouse event
        setMouseCallback("My Window", MyCallBackFunc2, &src);

        //show the image
        imshow("My Window", src);

        // Wait until user press some key
        waitKey(0);
    }
}

void lab4ex2(Mat src, int TH_aria, int phi_LOW, int phi_HIGH) {
    std::vector<Vec3b> pixels;
    for (int i = 0; i < src.rows; i++) {
        for (int j = 0; j < src.cols; j++) {
            Vec3b label = src.at<Vec3b>(i, j);
            if (!std::count(pixels.begin(), pixels.end(), label))
                pixels.push_back(label);
        }
    }


    int xc = 0, yc = 0;
    std::vector<Vec3b> newPixels;
    for (int i = 0; i < src.rows; i++) {
        for (int j = 0; j < src.cols; j++) {
            Vec3b crt_color = src.at<Vec3b>(i, j);
            if (crt_color == pixels[i]) {

                xc += j;
                yc += i;

            }
        }
    }


    Mat dst = Mat(src.rows, src.cols, CV_8UC3);
    for (int i = 0; i < pixels.size(); i++) {
        int area = calcAria(src, pixels[i]);
        int xcnorm = xc / area;
        int ycnorm = yc / area;
        double axa = calcAxaAlungire(src, pixels[i], xcnorm, ycnorm);
        int grade = axa * 180 / PI;
        printf("ana %2.f", axa);
        printf("ana %d", area);
        if (area < TH_aria && phi_LOW < grade && grade > phi_HIGH)
            newPixels.push_back(pixels[i]);
    }


    for (int i = 0; i < src.rows; i++) {
        for (int j = 0; j < src.cols; j++) {
            Vec3b label = src.at<Vec3b>(i, j);
            if (std::count(newPixels.begin(), newPixels.end(), label))
                dst.at<Vec3b>(i, j) = src.at<Vec3b>(i, j);
        }
    }
    imshow("new image", dst);
}

std::vector<Point2i> get4Neighbours(int i, int j) {
    int di[4] = {-1, 0, 1, 0};
    int dj[4] = {0, -1, 0, 1};
    std::vector<Point2i> neighbours;
    for (int k = 0; k < 4; k++) {
        neighbours.push_back({i + di[k], j + dj[k]});
    }
    return neighbours;
}

std::vector<Point2i> get8Neighbours(int i, int j) {
    int di[8] = {0, -1, -1, -1, 0, 1, 1, 1};
    int dj[8] = {1, 1, 0, -1, -1, -1, 0, 1};
    std::vector<Point2i> neighbours;;
    for (int k = 0; k < 8; k++) {
        neighbours.push_back({i + di[k], j + dj[k]});
    }
    return neighbours;
}


std::vector<Point2i> getPNeighbours(int i, int j) {
    int di[4] = {-1, -1, -1, 0};
    int dj[4] = {-1, 0, 1, -1};
    std::vector<Point2i> neighbours;
    for (int k = 0; k < 4; k++) {
        neighbours.push_back({i + di[k], j + dj[k]});
    }
    return neighbours;
}

Mat algoritm1(Mat img, bool ok) {
    int label = 0;

    Mat dst = Mat(img.rows, img.cols, CV_8UC3, Scalar(0, 0, 0));
    Mat labels = Mat::zeros(img.rows, img.cols, CV_8UC1);
    uchar val;

    for (int i = 1; i < img.rows - 1; i++) {
        for (int j = 1; j < img.cols - 1; j++) {
            val = img.at<uchar>(i, j);
            if ((val == 0) && (labels.at<uchar>(i, j) == 0)) {
                label++;
                std::queue<Point> Q;
                labels.at<uchar>(i, j) = label;
                Q.push({i, j});
                while (!Q.empty()) {
                    Point q = Q.front();
                    Q.pop();
                    std::vector<Point2i> neighbours = ok ? get4Neighbours(q.x, q.y) : get8Neighbours(q.x, q.y);
                    for (int n = 0; n < neighbours.size(); n++) {
                        if ((img.at<uchar>(neighbours[n].x, neighbours[n].y) == 0) &&
                            (labels.at<uchar>(neighbours[n].x, neighbours[n].y) == 0)) {
                            labels.at<uchar>(neighbours[n].x, neighbours[n].y) = label;
                            Q.push({neighbours[n].x, neighbours[n].y});
                        }

                    }
                }
            }
        }
    }


    default_random_engine gen;

    std::uniform_int_distribution<int> d(0, 255);
    for (int k = 1; k <= label; k++) {
        uchar red = d(gen);
        uchar blue = d(gen);
        uchar green = d(gen);
        Vec3b color = Vec3b(blue, green, red);
        for (int i = 1; i < img.rows - 1; i++) {
            for (int j = 1; j < img.cols - 1; j++) {
                if (labels.at<uchar>(i, j) == k)
                    dst.at<Vec3b>(i, j) = color;
            }
        }
    }
    return dst;


}

void showBfs() {
    Mat src;

    char fname[MAX_PATH];
    while (openFileDlg(fname)) {
        src = imread(fname, IMREAD_GRAYSCALE);
        bool ok;
        printf("For N4->4 or N8->8: ");
        int nr;
        scanf("%d", &nr);
        if (nr == 4) {
            ok = true;
        } else if (nr == 8) {
            ok = false;
        } else {
            return;
        }
        Mat dst = algoritm1(src, ok);

        imshow("source", src);
        imshow("labeled", dst);
        waitKey(0);
    }

}

Mat algortim2(Mat img) {
    int label = 0;


    Mat dst = Mat(img.rows, img.cols, CV_8UC3, Scalar(0, 0, 0));
    Mat dst2 = Mat(img.rows, img.cols, CV_8UC3, Scalar(0, 0, 0));
    Mat labels = Mat::zeros(img.rows, img.cols, CV_32SC1);
    Mat labels1 = Mat::zeros(img.rows, img.cols, CV_32SC1);
    std::vector<std::vector<int>> edges;

    edges.resize(label + 1);
    for (int i = 0; i < img.rows; i++) {
        for (int j = 0; j < img.cols; j++) {
            if ((img.at<uchar>(i, j) == 0) && (labels.at<int>(i, j) == 0)) {
                std::vector<int> L;
                std::vector<Point2i> neighbours = getPNeighbours(i, j);
                for (int n = 0; n < neighbours.size(); n++) {
                    if (labels.at<int>(neighbours[n].x, neighbours[n].y) > 0) {
                        L.push_back(labels.at<int>(neighbours[n].x, neighbours[n].y));

                    }
                }
                if (L.empty()) {
                    label++;
                    edges.resize(label + 1);
                    labels.at<int>(i, j) = label;
                } else {
                    int min = *std::min_element(L.begin(), L.end());
                    labels.at<int>(i, j) = min;
                    for (int y: L) {
                        if (y != min) {
                            edges.at(min).push_back(y);
                            edges.at(y).push_back(min);
                        }

                    }

                }

            }
        }

    }

    labels1 = labels.clone();
    int newlabel = 0;

    int *newLabels = (int *) calloc(label + 1, sizeof(int));
    for (int i = 1; i < label + 1; i++) {
        if (newLabels[i] == 0) {
            newlabel++;
            std::queue<int> Q;
            newLabels[i] = newlabel;
            Q.push(i);
            while (!Q.empty()) {
                int q = Q.front();
                Q.pop();
                for (int y: edges.at(q)) {
                    if (newLabels[y] == 0) {
                        newLabels[y] = newlabel;
                        Q.push(y);
                    }
                }
            }

        }
    }
    for (int i = 0; i < img.rows; i++) {
        for (int j = 0; j < img.cols; j++) {
            labels.at<int>(i, j) = newLabels[labels.at<int>(i, j)];
        }
    }


    default_random_engine gen;

    std::uniform_int_distribution<int> d(0, 255);
    for (int k = 1; k <= label; k++) {
        uchar red = d(gen);
        uchar blue = d(gen);
        uchar green = d(gen);
        Vec3b color = Vec3b(blue, green, red);
        for (int i = 1; i < img.rows - 1; i++) {
            for (int j = 1; j < img.cols - 1; j++) {
                if (labels.at<int>(i, j) == k)
                    dst.at<Vec3b>(i, j) = color;
                if (labels1.at<int>(i, j) == k)
                    dst2.at<Vec3b>(i, j) = color;
            }
        }
    }
    imshow("First", dst2);
    return dst;

}


void showAdancime2() {
    Mat src;

    char fname[MAX_PATH];
    while (openFileDlg(fname)) {
        src = imread(fname, IMREAD_GRAYSCALE);
        Mat dst = algortim2(src);
        Mat dst1 = algoritm1(src, true);
        imshow("Two", dst);
        imshow("BFS", dst1);
        imshow("source", src);


        waitKey(0);
    }

}

Point2i getStartPoint(Mat img) {
    for (int i = 0; i < img.rows; i++) {
        for (int j = 0; j < img.cols; j++) {
            if (img.at<uchar>(i, j) == 0)
                return Point2i(i, j);
        }
    }

}


vector<int> trace_border(bool i) {
    vector<int> code;
    char fname[MAX_PATH];
    while (openFileDlg(fname)) {
        Mat img = imread(fname, CV_LOAD_IMAGE_GRAYSCALE);

        Mat dst = Mat(img.rows, img.cols, CV_8UC1);

        Point2i startPoint = getStartPoint(img);
        Point2i currentPoint = startPoint;
        Point2i nextPoint;
        vector<Point2i> border;

        int dir = 7;
        border.push_back(startPoint);

        do {
            if (dir % 2 == 0)
                dir = (dir + 7) % 8;
            else
                dir = (dir + 6) % 8;

            std::vector<Point2i> neighbours = get8Neighbours(currentPoint.x, currentPoint.y);
            nextPoint = {neighbours[dir].x, neighbours[dir].y};
            while (img.at<uchar>(nextPoint.x, nextPoint.y) == 255) {
                dir = (dir + 1) % 8;
                nextPoint = {neighbours[dir].x, neighbours[dir].y};
            }
            code.push_back(dir);
            border.push_back(nextPoint);
            currentPoint = nextPoint;


        } while (border.size() <= 2 ||
                 (border.at(0) != border.at(border.size() - 2) && border.at(1) != border.at(border.size() - 1)));


        for (Point2i p: border) {
            dst.at<uchar>(p.x, p.y) = 255;
        }
        if (i) {
            imshow("final image", dst);
            waitKey(0);
            exit(1);
        }

        return code;
    }


}

void showDerivate() {
    vector<int> code = trace_border(false);
    vector<int> derivate;
    printf("Chain code: \n");

    for (int i = 0; i < code.size() - 1; i++) {

        derivate.push_back((code.at(i + 1) - code.at(i) + 8) % 8);
        printf("%d ", code.at(i));

    }
    printf("\nDerivative code:\n");
    for (int x: derivate) {
        printf("%d ", x);
    }

}

void reconstruct() {
    char fname[MAX_PATH];
    Mat img;

    if (openFileDlg(fname)) {
        img = imread(fname, CV_LOAD_IMAGE_GRAYSCALE);
    }
    int startX, startY, n;
    std::ifstream file;

    file.open("/Users/popruxi/Desktop/PI-L6/reconstruct.txt");
    file >> startX;
    file >> startY;
    file >> n;

    int c;
    vector<int> code;
    vector<Point2i> border;

    Point2i start = Point2i(startX, startY);
    Point2i currentPoint = start;
    Point2i nextPoint;
    for (int i = 0; i < n; i++) {

        file >> c;
        code.push_back(c);
    }

    border.push_back(start);
    for (int i = 0; i < n; i++) {
        std::vector<Point2i> neighbours = get8Neighbours(currentPoint.x, currentPoint.y);
        nextPoint = {neighbours[code.at(i)].x, neighbours[code.at(i)].y};
        border.push_back(nextPoint);
        currentPoint = nextPoint;
    }


    imshow("original image", img);
    Mat copy = img.clone();

    for (Point p: border) {
        copy.at<uchar>(p.x, p.y) = 0;
    }
    imshow("final image", copy);
    waitKey(0);
}


/////////////////////////////////////////////////////////LAB 7//////////////////////////////////////////////////////////////////////////////

/////////////////////////EX 1 SI 2///////////////////////////////
Mat constructElemStructural() {
    Mat elemStructural(3, 3, CV_8UC1, Scalar(255));
    elemStructural.at<uchar>(0, 1) = 0;
    elemStructural.at<uchar>(1, 1) = 0;
    elemStructural.at<uchar>(1, 0) = 0;
    elemStructural.at<uchar>(1, 2) = 0;
    elemStructural.at<uchar>(2, 1) = 0;
    return elemStructural;
}

Mat convertToBinary(Mat src) {
    int height = src.rows;
    int width = src.cols;
    Mat dst = Mat(height, width, CV_8UC1);
    for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
            uchar val = src.at<uchar>(i, j);
            if (val < 128)
                dst.at<uchar>(i, j) = 0;
            else
                dst.at<uchar>(i, j) = 255;
        }
    }
    return dst;
}

Mat dilation(Mat src, Mat elemStructural) {
    Mat dst(src.rows, src.cols, CV_8UC1, Scalar(255));

    for (int i = 0; i < src.rows; i++) {
        for (int j = 0; j < src.cols; j++) {
            if (src.at<uchar>(i, j) == 0) {
                for (int x = 0; x < elemStructural.rows; x++) {
                    for (int y = 0; y < elemStructural.cols; y++) {
                        if (elemStructural.at<uchar>(x, y) == 0) {
                            int auxx = i + x - (elemStructural.rows / 2);
                            int auxy = j + y - (elemStructural.cols / 2);
                            if (isInside(src, auxx, auxy)) {
                                dst.at<uchar>(auxx, auxy) = 0;
                            }
                        }

                    }
                }
            }
        }
    }

    return dst;
}

void dilateN(bool ok) {
    int n = 1;
    char fname[MAX_PATH];
    while (openFileDlg(fname)) {
        if (ok) {
            printf("Write n=");
            scanf("%d", &n);

        }
        Mat elemStructural = constructElemStructural();
        Mat src = imread(fname, IMREAD_GRAYSCALE);
        src = convertToBinary(src);
        Mat dst = dilation(src, elemStructural);

        for (int i = 0; i < n - 1; i++) {
            dst = dilation(dst, elemStructural);

        }

        imshow("Before", src);
        imshow("After", dst);
        waitKey();
    }
}

Mat erosion(Mat src, Mat elemStructural) {
    Mat dst = src.clone();

    for (int i = 0; i < src.rows; i++) {
        for (int j = 0; j < src.cols; j++) {
            if (src.at<uchar>(i, j) == 0) {
                for (int x = 0; x < elemStructural.rows; x++) {
                    for (int y = 0; y < elemStructural.cols; y++) {
                        int auxx = i + x - (elemStructural.rows / 2);
                        int auxy = j + y - (elemStructural.cols / 2);
                        if (elemStructural.at<uchar>(x, y) == 0 && src.at<uchar>(auxx, auxy) == 255) {
                            dst.at<uchar>(i, j) = 255;
                        }

                    }
                }

            }
        }
    }

    return dst;
}

void erosionN(bool ok) {
    int n = 1;


    char fname[MAX_PATH];
    while (openFileDlg(fname)) {
        if (ok) {
            printf("Write n=");
            scanf("%d", &n);

        }

        Mat src = imread(fname, IMREAD_GRAYSCALE);
        Mat elemStructural = constructElemStructural();
        src = convertToBinary(src);
        Mat dst = erosion(src, elemStructural);

        for (int i = 0; i < n - 1; i++) {
            dst = erosion(dst, elemStructural);

        }

        imshow("Before", src);
        imshow("After", dst);
        waitKey();
    }
}

Mat closeImage(Mat src, Mat elemStructural) {
    Mat dst = dilation(src, elemStructural);
    dst = erosion(dst, elemStructural);
    return dst;
}

void closeImageN(bool ok) {
    int n = 1;
    char fname[MAX_PATH];
    while (openFileDlg(fname)) {
        if (ok) {
            printf("Write n=");
            scanf("%d", &n);

        }

        Mat src = imread(fname, IMREAD_GRAYSCALE);
        Mat elemStructural = constructElemStructural();
        src = convertToBinary(src);
        Mat dst = closeImage(src, elemStructural);
        for (int i = 0; i < n - 1; i++) {
            dst = closeImage(dst, elemStructural);

        }

        imshow("Before", src);
        imshow("After", dst);
        waitKey();

    }

}

Mat openImage(Mat src, Mat elemStructural) {
    Mat dst = erosion(src, elemStructural);
    dst = dilation(dst, elemStructural);
    return dst;
}

void openImageN(bool ok) {
    char fname[MAX_PATH];
    int n = 1;
    while (openFileDlg(fname)) {
        if (ok) {
            printf("Write n=");
            scanf("%d", &n);

        }
        Mat elemStructural = constructElemStructural();
        Mat src = imread(fname, IMREAD_GRAYSCALE);
        src = convertToBinary(src);

        Mat dst = openImage(src, elemStructural);
        for (int i = 0; i < n - 1; i++) {
            dst = openImage(dst, elemStructural);

        }

        imshow("Before", src);
        imshow("After", dst);
        waitKey();

    }

}

/////////////////////////////////////////////////////////MATRIX OP//////////////////////////////////////////////////////////////////////////////
Mat subtract(Mat A, Mat B) {
    Mat dst = Mat(A.rows, B.cols, CV_8UC1);
    for (int i = 0; i < A.rows; i++)
        for (int j = 0; j < A.cols; j++)
            dst.at<uchar>(i, j) = 255;
    for (int i = 0; i < A.rows; i++) {
        for (int j = 0; j < A.cols; j++) {
            if (A.at<uchar>(i, j) == 0)
                if (B.at<uchar>(i, j) == 255)
                    dst.at<uchar>(i, j) = 0;
        }
    }
    return dst;
}


Mat complement(Mat A) {
    Mat dst = Mat(A.rows, A.cols, CV_8UC1);
    for (int i = 0; i < A.rows; i++)
        for (int j = 0; j < A.cols; j++)
            if (A.at<uchar>(i, j) == 0) {
                dst.at<uchar>(i, j) = 255;
            } else {
                dst.at<uchar>(i, j) = 0;
            }
    return dst;
}

Mat intersection(Mat A, Mat B) {
    Mat dst = Mat(A.rows, B.cols, CV_8UC1);
    for (int i = 0; i < A.rows; i++)
        for (int j = 0; j < A.cols; j++)
            if (A.at<uchar>(i, j) == B.at<uchar>(i, j) && B.at<uchar>(i, j) == 0) {
                dst.at<uchar>(i, j) = 0;
            } else {
                dst.at<uchar>(i, j) = 255;
            }
    return dst;
}

bool equalImages(Mat A, Mat B) {
    for (int i = 0; i < A.rows; i++) {
        for (int j = 0; j < A.cols; j++) {
            if (A.at<uchar>(i, j) != B.at<uchar>(i, j))
                return false;
        }
    }
    return true;
}

Mat unionImages(Mat A, Mat B) {
    Mat dst = Mat(A.rows, B.cols, CV_8UC1);
    for (int i = 0; i < A.rows; i++) {
        for (int j = 0; j < A.cols; j++) {
            if (A.at<uchar>(i, j) == 0 || B.at<uchar>(i, j) == 0)
                dst.at<uchar>(i, j) = 0;
            else
                dst.at<uchar>(i, j) = 255;
        }
    }
    return dst;
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////EX 3///////////////////////////////

void boundaryExtragery() {
    char fname[MAX_PATH];
    while (openFileDlg(fname)) {
        Mat src = imread(fname, IMREAD_GRAYSCALE);
        src = convertToBinary(src);
        Mat elemStructural(3, 3, CV_8UC1, Scalar(0));
        Mat ero = erosion(src, elemStructural);
        Mat dst = subtract(src, ero);
        imshow("src", src);
        imshow("dst", dst);
        waitKey(0);

    }

}

/////////////////////////EX 4 ///////////////////////////////
Mat fill(Mat src, Mat elemStructural) {
    Mat Xk = Mat(src.rows, src.cols, CV_8UC1, Scalar(255));
    Xk.at<uchar>(src.rows / 2, src.cols / 2) = 0;
    Mat imageA = complement(src);
    Mat Xk_1 = Xk;
    Xk = intersection(dilation(Xk, elemStructural), imageA);
    while (!equalImages(Xk, Xk_1)) {
        Xk_1 = Xk;
        Xk = intersection(dilation(Xk, elemStructural), imageA);
    }
    return unionImages(Xk, src);

}

void fillRegion() {
    char fname[MAX_PATH];
    while (openFileDlg(fname)) {
        Mat src = imread(fname, IMREAD_GRAYSCALE);
        src = convertToBinary(src);
        Mat elemStructural = constructElemStructural();
        Mat dst = fill(src, elemStructural);
        imshow("src", src);
        imshow("dst", dst);
        waitKey(0);

    }


}

/////////////////////////////////////////////////////////LAB 8//////////////////////////////////////////////////////////////////////////////
////////////////EX1//////////////////


float calcMedia(Mat img) {
    float media = 0;

    for (int i = 0; i < img.rows; i++) {
        for (int j = 0; j < img.cols; j++) {
            media += img.at<uchar>(i, j);
        }
    }
    media = media / (img.rows * img.cols);

    return media;
}

float calcDev(Mat img) {
    float deviatia = 0;
    float media = calcMedia(img);
    for (int i = 0; i < img.rows; i++) {
        for (int j = 0; j < img.cols; j++) {
            deviatia += pow((img.at<uchar>(i, j) - media), 2);
        }
    }

    deviatia /= (img.rows * img.cols);
    return sqrt(deviatia);
}

void afisare() {
    char fname[MAX_PATH];
    while (openFileDlg(fname)) {
        Mat img = imread(fname, CV_LOAD_IMAGE_GRAYSCALE);
        float media = calcMedia(img);
        float dev = calcDev(img);
        printf("Media este := %.2f ", media);
        printf("Dev este := %.2f ", dev);
        waitKey(0);
    }
}

void cumulative_histogram() {
    char fname[MAX_PATH];
    while (openFileDlg(fname)) {
        Mat img = imread(fname, CV_LOAD_IMAGE_GRAYSCALE);
        int *histogram = computeHistogram(img);

        int *histogram_C = (int *) calloc(256, sizeof(int));
        for (int g = 0; g < 256; g++) {
            for (int j = 0; j <= g; j++) {
                histogram_C[g] += histogram[j];
            }
        }
        imshow("Source", img);
        showHistogram("Histogram", histogram, 256, 256);
        showHistogram("Cumulative Histogram", histogram_C, 256, 256);
        waitKey(0);
    }
}

////////////////EX2//////////////////
void hist_binarizata() {
    float mg1 = 0, mg2 = 0;
    int N1 = 0, N2 = 0;
    float error;
    printf("\nIntroduceti error:");
    scanf("%f", &error);
    char fname[MAX_PATH];
    while (openFileDlg(fname)) {
        Mat img = imread(fname, CV_LOAD_IMAGE_GRAYSCALE);
        Mat dst = Mat(img.rows, img.cols, CV_8UC1);
        int *histogram = computeHistogram(img);
        int min = histogram[0];
        int max = histogram[0];
        bool found = false;
        for (int i = 0; i < 256; ++i) {
            if (histogram[i] != 0) {
                if (found == false) {
                    min = i;
                    found = true;
                } else {
                    max = i;
                }

            }
        }
        float Tk = (min + max) / 2;
        float Tk_1 = 0;
        do {
            for (int g = min; g < Tk; g++) {
                mg1 += g * histogram[g];
                N1 += histogram[g];
            }

            for (int g = Tk + 1; g < max; g++) {
                mg2 += g * histogram[g];
                N2 += histogram[g];
            }

            mg1 = mg1 / N1;
            mg2 = mg2 / N2;

            Tk_1 = Tk;
            Tk = (mg1 + mg2) / 2;
        } while (abs(Tk - Tk_1) < error);
        for (int i = 0; i < img.rows; i++)
            for (int j = 0; j < img.cols; j++) {
                if (img.at<uchar>(i, j) < Tk)
                    dst.at<uchar>(i, j) = 0;
                else
                    dst.at<uchar>(i, j) = 255;
            }
        imshow("Source", img);
        //showHistogram("Histogram", histogram, 256, 200);
        printf("pragul este: %.2f", Tk);
        imshow("Destination", dst);
        waitKey(0);
    }
}


////////////////EX3//////////////////

void negativul_imagini() {
    char fname[MAX_PATH];
    while (openFileDlg(fname)) {
        Mat img = imread(fname, CV_LOAD_IMAGE_GRAYSCALE);
        Mat dst = Mat(img.rows, img.cols, CV_8UC1);


        for (int i = 0; i < img.rows; i++)
            for (int j = 0; j < img.cols; j++) {
                int gin = img.at<uchar>(i, j);
                int gout = 255 - gin;
                if (gout < 0)
                    gout = 0;
                if (gout > 255)
                    gout = 255;

                dst.at<uchar>(i, j) = gout;
            }

        imshow("Source", img);
        imshow("Destination", dst);
        waitKey(0);
    }
}

void modificare_luminozitate() {
    char fname[MAX_PATH];
    float offset;
    printf("\nIntroduceti offset:");
    scanf("%f", &offset);
    while (openFileDlg(fname)) {
        Mat img = imread(fname, CV_LOAD_IMAGE_GRAYSCALE);
        Mat dst = Mat(img.rows, img.cols, CV_8UC1);


        for (int i = 0; i < img.rows; i++)
            for (int j = 0; j < img.cols; j++) {
                int gin = img.at<uchar>(i, j);
                int gout = gin + offset;
                if (gout < 0)
                    gout = 0;
                if (gout > 255)
                    gout = 255;

                dst.at<uchar>(i, j) = gout;
            }

        imshow("Source", img);
        imshow("Destination", dst);
        waitKey(0);
    }
}

void modificare_contrast() {
    char fname[MAX_PATH];
    int gout_min, gout_max;
    printf("\nIntroduceti gout_min:");
    scanf("%d", &gout_min);
    printf("\nIntroduceti gout_max:");
    scanf("%d", &gout_max);
    while (openFileDlg(fname)) {
        Mat img = imread(fname, CV_LOAD_IMAGE_GRAYSCALE);
        Mat dst = Mat(img.rows, img.cols, CV_8UC1);

        int *histogram = computeHistogram(img);
        int *histo_after = (int *) calloc(sizeof(int), 256);

        int gin_min = 0, gin_max = 0;
        bool foundMin = false;
        for (int i = 0; i < 256; i++) {
            if (histogram[i] != 0) {
                if (foundMin == false) {
                    gin_min = i;
                    foundMin = true;
                } else
                    gin_max = i;
            }
        }

        for (int i = 0; i < img.rows; i++)
            for (int j = 0; j < img.cols; j++) {
                int gin = img.at<uchar>(i, j);
                int gout = gout_min + (gin - gin_min) * (gout_max - gout_min) / (gin_max - gin_min);
                if (gout < 0)
                    gout = 0;
                if (gout > 255)
                    gout = 255;
                histo_after[gout] = histogram[gin];
                dst.at<uchar>(i, j) = gout;
            }

        imshow("Source", img);
        showHistogram("histogram_before", histogram, 256, 200);
        showHistogram("histogram_after", histo_after, 256, 200);
        imshow("Destination", dst);
        waitKey(0);
    }
}

void corectie_gamma() {
    int hist[256] = {};
    char fname[MAX_PATH];
    float gamma;
    printf("\nIntroduceti gamma:");
    scanf("%f", &gamma);
    if (openFileDlg(fname)) {
        Mat img = imread(fname, CV_LOAD_IMAGE_GRAYSCALE);

        Mat dst = Mat(img.rows, img.cols, CV_8UC1);

        int *histogram = computeHistogram(img);

        float L = 255;
        int *histo_after = (int *) calloc(sizeof(int), 256);
        for (int i = 0; i < img.rows; i++)
            for (int j = 0; j < img.cols; j++) {
                int gin = img.at<uchar>(i, j);
                int gout = L * pow(gin / L, gamma);
                if (gout < 0)
                    gout = 0;
                if (gout > 255)
                    gout = 255;
                dst.at<uchar>(i, j) = gout;
                //hist_after[gout] = histogram[gin];
            }
        imshow("Source", img);
        //   showHistogram("histogram_before", histogram, 256, 200);
        // showHistogram("histogram_after", histo_after, 256, 200);
        imshow("Destination", dst);
        waitKey(0);
    }
}

////////////////EX4//////////////////
void egalizare_histograma() {
    char fname[MAX_PATH];
    float fdpc[256] = {};
//   int * histo_after= (int *) calloc(sizeof(int), 256);
    while (openFileDlg(fname)) {
        Mat img = imread(fname, CV_LOAD_IMAGE_GRAYSCALE);
        Mat dst = Mat(img.rows, img.cols, CV_8UC1);
        int *histogram = computeHistogram(img);
        for (int k = 0; k < 255; ++k) {
            for (int g = 0; g < k; ++g) {
                fdpc[k] += histogram[g];

            }

            //  histo_after[k]=fdpc[k];
            fdpc[k] /= (img.rows * img.cols);
        }
        for (int i = 0; i < img.rows; i++)
            for (int j = 0; j < img.cols; j++) {
                int gin = img.at<uchar>(i, j);
                int gout = 255 * fdpc[gin];
                dst.at<uchar>(i, j) = gout;
            }
        showHistogram("histogram_before", histogram, 255, 200);
        //   showHistogram("histogram_after", histo_after, 255, 200);
        imshow("Source", img);

        imshow("Destination", dst);
        waitKey(0);

    }


}

/////////////////////////////////////////////////////////LAB 7//////////////////////////////////////////////////////////////////////////////
///////////////EX1
int **getNucleu(int type) {
    int **kernel = (int **) calloc(3, sizeof(int *));
    for (int i = 0; i < 3; i++)
        kernel[i] = (int *) calloc(3, sizeof(int));

    switch (type) {
        case 1:
            kernel[0][0] = 1;
            kernel[0][1] = 1;
            kernel[0][2] = 1;
            kernel[1][0] = 1;
            kernel[1][1] = 1;
            kernel[1][2] = 1;
            kernel[2][0] = 1;
            kernel[2][1] = 1;
            kernel[2][2] = 1;
            return kernel;
        case 2:
            kernel[0][0] = 1;
            kernel[0][1] = 2;
            kernel[0][2] = 1;
            kernel[1][0] = 2;
            kernel[1][1] = 4;
            kernel[1][2] = 2;
            kernel[2][0] = 1;
            kernel[2][1] = 2;
            kernel[2][2] = 1;
            return kernel;
        case 3:
            kernel[0][0] = 0;
            kernel[0][1] = -1;
            kernel[0][2] = 0;
            kernel[1][0] = -1;
            kernel[1][1] = 4;
            kernel[1][2] = -1;
            kernel[2][0] = 0;
            kernel[2][1] = -1;
            kernel[2][2] = 0;
            return kernel;
        case 4:
            kernel[0][0] = -1;
            kernel[0][1] = -1;
            kernel[0][2] = -1;
            kernel[1][0] = -1;
            kernel[1][1] = 8;
            kernel[1][2] = -1;
            kernel[2][0] = -1;
            kernel[2][1] = -1;
            kernel[2][2] = -1;
            return kernel;
        case 5:
            kernel[0][0] = 0;
            kernel[0][1] = -1.0;
            kernel[0][2] = 0;
            kernel[1][0] = -1.0;
            kernel[1][1] = 5.0;
            kernel[1][2] = -1.0;
            kernel[2][0] = 0;
            kernel[2][1] = -1.0;
            kernel[2][2] = 0;
            return kernel;
        default:
            kernel[0][0] = -1.0;
            kernel[0][1] = -1.0;
            kernel[0][2] = -1.0;
            kernel[1][0] = -1.0;
            kernel[1][1] = 9.0;
            kernel[1][2] = -1.0;
            kernel[2][0] = -1.0;
            kernel[2][1] = -1.0;
            kernel[2][2] = -1.0;
            return kernel;
    }
}

float getScalar(bool ok, int **nucleu) {
    float sum = 0;
    int Sp = 0;
    int Sn = 0;
    for (int i = 0; i < 3; i++) {
        for (int j = 0; j < 3; j++) {
            if (ok) {
                sum = sum + nucleu[i][j];
            } else {
                if (nucleu[i][j] > 0)
                    Sp += nucleu[i][j];
                else
                    Sn += -nucleu[i][j];
            }
        }
    }
    if (!ok) {
        sum = 1.0 / (2 * max(Sp, Sn));

    }
    return sum;

}

Mat getFilter(bool ok, Mat src, int **nucleu, float constant) {
    Mat dst = Mat(src.rows, src.cols, CV_8UC1);
    for (int i = 0 + 1; i < src.rows - 1; i++)
        for (int j = 0 + 1; j < src.cols - 1; j++) {
            int Id = 0;
            for (int u = 0; u < 3; u++)
                for (int v = 0; v < 3; v++) {
                    int Is = src.at<uchar>(i + u - 1, j + v - 1);
                    Id += nucleu[u][v] * Is;
                }
            if (ok) {
                Id /= constant;
            } else {
                Id = constant * Id + 255 / 2;
            }
            if (Id < 0) Id = 0;
            if (Id > 255) Id = 255;

            (dst).at<uchar>(i, j) = Id;
        }
    return dst;
}

Mat low_filter(Mat src, int type) {
    int **nucleu = getNucleu(type);
    float scalar = getScalar(true, nucleu);
    Mat dst = getFilter(true, src, nucleu, scalar);
    return dst;
}

Mat high_filter(Mat src, int type) {
    int **nucleu = getNucleu(type);
    float scalar = getScalar(false, nucleu);
    Mat dst = getFilter(false, src, nucleu, scalar);
    return dst;
}


void filtruGen() {

    printf("1. Mean filter\n");
    printf("2. Gaussian filter\n");
    printf("3. Laplace filter 1\n");
    printf("4. Laplace filter 2\n");
    printf("5. High-pass filter 1\n");
    printf("6. High-pass filter 2\n");
    printf("Select type:");
    char fname[MAX_PATH];
    int type;
    scanf("%d", &type);
    while (openFileDlg(fname)) {
        Mat src = imread(fname, IMREAD_GRAYSCALE);
        Mat dst = src.clone();
        if (type >= 1 && type <= 2) {
            dst = low_filter(src, type);
        }
        if (type >= 3) {
            dst = high_filter(src, type);
        }
        imshow("Source", src);
        imshow("Dst", dst);

        waitKey();
    }


}


Mat centering_transform(Mat img) {
    Mat img2 = img.clone();
    for (int i = 0; i < img2.rows; i++) {
        for (int j = 0; j < img2.cols; j++) {
            img2.at<float>(i, j) = ((i + j) & 1) ? -img2.at<float>(i, j) : img2.at<float>(i, j);
        }
    }
    return img2;
}


Mat generic_frecquency_domain_filter(Mat src, int type) {

    Mat srcf;
    src.convertTo(srcf, CV_32FC1);
    srcf = centering_transform(srcf);
    Mat fourier;
    dft(srcf, fourier, DFT_COMPLEX_OUTPUT);
    Mat channels[] = {Mat::zeros(src.size(), CV_32F), Mat::zeros(src.size(), CV_32F)};
    split(fourier, channels);
    Mat mag, phi;
    magnitude(channels[0], channels[1], mag);
    phase(channels[0], channels[1], phi);
    mag += Scalar::all(1);
    log(mag, mag);
    Mat spectrum;
    normalize(mag, spectrum, 0, 255, NORM_MINMAX, CV_8UC1);
    int poz;
    float R = 20;
    int height = src.rows;
    int width = src.cols;
    switch (type) {
        case 1:
            imshow("Magnitudine", spectrum);
            break;
        case 2:
            for (int i = 0; i < src.rows; i++) {
                for (int j = 0; j < src.cols; j++) {
                    poz = (height / 2 - i) * (height / 2 - i) + (width / 2 - j) * (width / 2 - j);
                    if (poz > R * R) {
                        channels[0].at<float>(i, j) = 0.0f;
                        channels[1].at<float>(i, j) = 0.0f;
                        mag.at<float>(i, j) = 0;
                    }

                }
            }
            imshow("Magnitude", mag);
            break;
        case 3:
            for (int i = 0; i < src.rows; i++) {
                for (int j = 0; j < src.cols; j++) {
                    poz = ((float) height / 2 - i) * ((float) height / 2 - i) +
                          ((float) width / 2 - j) * ((float) width / 2 - j);
                    if (poz < R * R) {
                        channels[0].at<float>(i, j) = 0.0f;
                        channels[1].at<float>(i, j) = 0.0f;
                        mag.at<float>(i, j) = 0;
                    }

                }
            }
            imshow("Magnitude", mag);
            break;
        case 4:
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    poz = (height / 2 - i) * (height / 2 - i) +
                          (width / 2 - j) * (width / 2 - j);
                    float coef = exp(-poz / (R * R));
                    channels[0].at<float>(i, j) *= coef;
                    channels[1].at<float>(i, j) *= coef;
                    mag.at<float>(i, j) *= coef;
                }
            }
            imshow("Magnitude", mag);
            break;
        case 5:
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    poz = (height / 2 - i) * (height / 2 - i) +
                          (width / 2 - j) * (width / 2 - j);
                    float coef = 1 - exp(-poz / (R * R));
                    channels[0].at<float>(i, j) *= coef;
                    channels[1].at<float>(i, j) *= coef;
                    mag.at<float>(i, j) *= coef;
                }
            }
            imshow("Magnitude", mag);
            break;
    }

    Mat dst, dstf;
    merge(channels, 2, fourier);
    dft(fourier, dstf, DFT_INVERSE | cv::DFT_REAL_OUTPUT | DFT_SCALE);
    dstf = centering_transform(dstf);
    normalize(dstf, dst, 0, 255, NORM_MINMAX, CV_8UC1);
    // dstf.convertTo(dst, CV_8UC1);

    return dst;


}

void frec_domain() {
    printf("1.Magnitudine\n");
    printf("2.Filtru ideal trece-jos\n");
    printf("3.Filtru ideal trece-sus\n");
    printf("4.Filtru Gaussian trece-jos\n");
    printf("5.Filtru Gaussian trece-sus\n");
    int type;
    scanf("%d", &type);
    char fname[MAX_PATH];
    while (openFileDlg(fname)) {
        Mat src = imread(fname, IMREAD_GRAYSCALE);

        Mat dst = generic_frecquency_domain_filter(src, type);
        imshow("Soruce", src);
        imshow("Dst", dst);
        waitKey(0);
    }
}


void quickSort(int arr[], int left, int right) {
    int i = left, j = right;
    int tmp;
    int pivot = arr[(left + right) / 2];

    /* partition */
    while (i <= j) {
        while (arr[i] < pivot)
            i++;
        while (arr[j] > pivot)
            j--;
        if (i <= j) {
            tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
            i++;
            j--;
        }
    };

    /* recursion */
    if (left < j)
        quickSort(arr, left, j);
    if (i < right)
        quickSort(arr, i, right);

}

Mat filtruMedian(Mat img) {
    double t = (double) getTickCount();
    printf("w=");
    int w;
    scanf("%d", &w);
    if (w != 3 && w != 5 && w != 7) {
        printf("Invalid value! (Correct values: 3 or 5 or 7)\n");
        exit(0);
    }
    int d = w / 2;
    Mat dst = img.clone();
    Mat dst1 = img.clone();
    Mat dst2 = img.clone();
    for (int i = d; i < img.rows - d; i++) {
        for (int j = d; j < img.cols - d; j++) {
            int *L = (int *) malloc(w * w * sizeof(int));
            int size = 0;
            for (int x = -d; x <= d; x++) {
                for (int y = -d; y <= d; y++) {

                    L[size] = img.at<uchar>(i + x, j + y);
                    size++;
                }
            }


            quickSort(L, 0, w * w - 1);
            dst.at<uchar>(i, j) = L[w * w / 2];
            dst1.at<uchar>(i, j) = L[0];
            dst2.at<uchar>(i, j) = L[w * w - 1];
            free(L);
        }
    }

    t = ((double) getTickCount() - t) / getTickFrequency();
    printf("Time = %.3f [ms] \n", t * 1000);
    imshow("Filtrare piper", dst1);
    imshow("Filtrare sare", dst2);
    return dst;
}

Mat filtruGaussianBi(Mat img) {
    double t = (double) getTickCount();
    printf("w= ");
    int w;
    scanf("%d", &w);
    if (w != 3 && w != 5 && w != 7) {
        printf("Invalid value! (Correct values: 3 or 5 or 7)\n");
        exit(0);
    }
    Mat dst = img.clone();
    float sigma = (float) w / 6;
    float miu = (float) w / 2;
    float **G = (float **) malloc(w * sizeof(float *));
    for (int i = 0; i < w; i++)
        G[i] = (float *) malloc(w * sizeof(float));

    for (int i = 0; i < w; i++) {
        for (int j = 0; j < w; j++) {
            float E = exp(-(float) (pow((i - miu), 2) + pow((j - miu), 2)) / (2.0 * pow(sigma, 2)));
            float N = (2 * PI * pow(sigma, 2));
            G[i][j] = E / N;


        }
    }
    for (int i = w / 2; i < img.rows - w / 2; i++) {
        for (int j = w / 2; j < img.cols - w / 2; j++) {
            float ID = 0.0;
            for (int x = -(w / 2); x <= w / 2; x++) {
                for (int y = -(w / 2); y <= w / 2; y++) {
                    ID += G[x + w / 2][y + w / 2] * img.at<uchar>(i + x, j + y);
                }

            }
            dst.at<uchar>(i, j) = ID;
        }
    }
    t = ((double) getTickCount() - t) / getTickFrequency();
    printf("Time = %.3f [ms] \n", t * 1000);
    return dst;
}

Mat filtruGaussianSep(Mat img) {
    double t = (double) getTickCount();
    printf("w= ");
    int w;
    scanf("%d", &w);
    if (w != 3 && w != 5 && w != 7) {
        printf("Invalid value! (Correct values: 3 or 5 or 7)\n");
        exit(0);
    }
    Mat dst = img.clone();

    float sum = 0.0;
    float H[9];
    int d = w / 2;
    float sigma = ((float) w) / 6.0f;
    for (int x = 0; x < w; x++) {
        float E = exp(-((x - d) * (x - d) / (2 * sigma * sigma)));
        float N = sigma * sqrt(2.0 * PI);
        H[x] = E / N;
        sum += H[x];
    }
    for (int i = d; i < img.rows - d; i++) {
        for (int j = d; j < img.cols - d; j++) {
            float ps = 0.0f;
            for (int m = -d; m <= d; m++) {
                ps += img.at<uchar>(i + m, j) * H[m + d];
            }
            dst.at<uchar>(i, j) = ps / sum;
        }
    }
    Mat dst1 = dst.clone();
    for (int i = d; i < img.rows - d; i++) {
        for (int j = d; j < img.cols - d; j++) {
            float ps = 0.0f;
            for (int m = -d; m <= d; m++) {
                ps += dst.at<uchar>(i, j + m) * H[m + d];
            }
            dst1.at<uchar>(i, j) = ps / sum;
        }
    }
    t = ((double) getTickCount() - t) / getTickFrequency();
    printf("Time = %.3f [ms] \n", t * 1000);
    return dst1;

}


void alegere_lab10() {
    printf("1.Filtru Median\n");
    printf("2.Nucleu gaussian bidimensional\n");
    printf("3.Nucleu gaussian separat in 2 componente vectoriale\n");
    int al;
    Mat dst;
    scanf("%d", &al);
    char fname[MAX_PATH];
    while (openFileDlg(fname)) {
        Mat src = imread(fname, 0);
        switch (al) {
            case 1:
                dst = filtruMedian(src);
                break;
            case 2:
                dst = filtruGaussianBi(src);
                break;
            case 3:
                dst = filtruGaussianSep(src);
                break;

        }
        imshow("Initial Image", src);
        imshow("Final Image", dst);
        waitKey();
    }
}

/////////////////////////////////////////////////////////LAB 11//////////////////////////////////////////////////////////////////////////////
///////EX1
Mat calcGrad(Mat src, int **kernel, int nb) {
    float grad;
    Mat dst = Mat(src.rows, src.cols, CV_8UC1);

    for (int i = 0 + 1; i < src.rows - 1; i++) {
        for (int j = 0 + 1; j < src.cols - 1; j++) {
            grad = 0;
            for (int u = 0; u < nb; u++)
                for (int v = 0; v < nb; v++) {

                    grad += kernel[u][v] * src.at<uchar>(i + u - 1, j + v - 1);

                }

            if (grad < 0) grad = 0;
            if (grad > 255) grad = 255;
            dst.at<uchar>(i, j) = grad;
        }
    }
    return dst;
}

void calcGradian1(Mat src) {
    int **kernel = (int **) calloc(3, sizeof(int *));
    for (int i = 0; i < 3; i++)
        kernel[i] = (int *) calloc(3, sizeof(int));
    kernel[0][0] = -1;
    kernel[0][1] = 0;
    kernel[0][2] = 1;
    kernel[1][0] = -1;
    kernel[1][1] = 0;
    kernel[1][2] = 1;
    kernel[2][0] = -1;
    kernel[2][1] = 0;
    kernel[2][2] = 1;
    Mat dstx = calcGrad(src, kernel, 3);
    int **kernel_1 = (int **) calloc(3, sizeof(int *));
    for (int i = 0; i < 3; i++)
        kernel_1[i] = (int *) calloc(3, sizeof(int));
    kernel_1[0][0] = 1;
    kernel_1[0][1] = 1;
    kernel_1[0][2] = 1;
    kernel_1[1][0] = 0;
    kernel_1[1][1] = 0;
    kernel_1[1][2] = 0;
    kernel_1[2][0] = -1;
    kernel_1[2][1] = -1;
    kernel_1[2][2] = -1;
    Mat dsty = calcGrad(src, kernel_1, 3);
    imshow("dstx", dstx);
    imshow("dsty", dsty);
}

void calcGradian2(Mat src) {
    int **kernel = (int **) calloc(3, sizeof(int *));
    for (int i = 0; i < 3; i++)
        kernel[i] = (int *) calloc(3, sizeof(int));
    kernel[0][0] = -1;
    kernel[0][1] = 0;
    kernel[0][2] = 1;
    kernel[1][0] = -2;
    kernel[1][1] = 0;
    kernel[1][2] = 2;
    kernel[2][0] = -1;
    kernel[2][1] = 0;
    kernel[2][2] = 1;
    Mat dstx = calcGrad(src, kernel, 3);
    int **kernel_1 = (int **) calloc(3, sizeof(int *));
    for (int i = 0; i < 3; i++)
        kernel_1[i] = (int *) calloc(3, sizeof(int));
    kernel_1[0][0] = 1;
    kernel_1[0][1] = 2;
    kernel_1[0][2] = 1;
    kernel_1[1][0] = 0;
    kernel_1[1][1] = 0;
    kernel_1[1][2] = 0;
    kernel_1[2][0] = -1;
    kernel_1[2][1] = -2;
    kernel_1[2][2] = -1;
    Mat dsty = calcGrad(src, kernel_1, 3);
    imshow("dstx", dstx);
    imshow("dsty", dsty);
}


void calcGradian3(Mat src) {
    int **kernel = (int **) calloc(2, sizeof(int *));
    for (int i = 0; i < 2; i++)
        kernel[i] = (int *) calloc(2, sizeof(int));
    kernel[0][0] = 1;
    kernel[0][1] = 0;
    kernel[1][0] = 0;
    kernel[1][1] = -1;


    Mat dstx = calcGrad(src, kernel, 2);
    int **kernel_1 = (int **) calloc(2, sizeof(int *));
    for (int i = 0; i < 2; i++)
        kernel_1[i] = (int *) calloc(2, sizeof(int));
    kernel_1[0][0] = 0;
    kernel_1[0][1] = -1;
    kernel_1[1][0] = 1;
    kernel_1[1][1] = 0;

    Mat dsty = calcGrad(src, kernel_1, 2);
    imshow("dstx", dstx);
    imshow("dsty", dsty);
}

int calcComp() {
    int type;
    printf("1.Prewitt\n");
    printf("2.Sobel\n");
    printf("3.Roberts\n");
    scanf("%d", &type);

    char fname[MAX_PATH];
    while (openFileDlg(fname)) {
        Mat src = imread(fname, IMREAD_GRAYSCALE);
        switch (type) {
            case 1:
                calcGradian1(src);
                break;
            case 2:
                calcGradian2(src);
                break;
            case 3:
                calcGradian3(src);
                break;
        }


        waitKey(0);
    }
}

///////EX2
Mat calcModul(Mat fx, Mat fy) {
    Mat dst = fx.clone();
    for (int i = 0; i < fx.rows; i++) {
        for (int j = 0; j < fx.cols; j++) {
            dst.at<uchar>(i, j) = sqrt(pow(fx.at<uchar>(i, j), 2) + pow(fy.at<uchar>(i, j), 2));
            if (dst.at<uchar>(i, j) < 0) dst.at<uchar>(i, j) = 0;
            if (dst.at<uchar>(i, j) > 255) dst.at<uchar>(i, j) = 255;
        }
    }
    return dst;

}

Mat calcDirectie(Mat fx, Mat fy, bool isRoberts) {
    Mat dst = fx.clone();
    for (int i = 0; i < fx.rows; i++) {
        for (int j = 0; j < fx.cols; j++) {
            float teta = atan((double) fx.at<uchar>(i, j) / fy.at<uchar>(i, j));
            int dir;
            if ((teta > 3 * PI / 8 && teta < 5 * PI / 8) || (teta > -5 * PI / 8 && teta < -3 * PI / 8))
                dir = 0;

            if ((teta > PI / 8 && teta < 3 * PI / 8) || (teta > -7 * PI / 8 && teta < -5 * PI / 8))
                dir = 1;

            if ((teta > -PI / 8 && teta < PI / 8) || teta > 7 * PI / 8 && teta < -7 * PI / 8)
                dir = 2;

            if ((teta > 5 * PI / 8 && teta < 7 * PI / 8) || (teta > -3 * PI / 8 && teta < -PI / 8))
                dir = 3;

            (dst).at<uchar>(i, j) = dir;

            if (isRoberts) {
                (dst).at<uchar>(i, j) += 135;
            }


//            if (dst.at<uchar>(i, j) < 0) dst.at<uchar>(i, j) = 0;
//            if (dst.at<uchar>(i, j) > 255) dst.at<uchar>(i, j) = 255;
        }

    }
    return dst;

}

Mat convertFromGrayToWB_2(Mat src) {//imagine binara -binarizare cu prag(thresholding)
    int threshold;
    printf("Threshold=");
    scanf("%d", &threshold);
    int height = src.rows;
    int width = src.cols;

    Mat dst = Mat(height, width, CV_8UC1);
    for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
            uchar valFinal;
            uchar val = src.at<uchar>(i, j);
            if (val < threshold) {
                dst.at<uchar>(i, j) = 0;//black
            } else if (val >= threshold) {
                dst.at<uchar>(i, j) = 255;
            }

        }
    }
    return dst;


}

void calcPrewit(Mat src) {
    int **kernel = (int **) calloc(3, sizeof(int *));
    for (int i = 0; i < 3; i++)
        kernel[i] = (int *) calloc(3, sizeof(int));
    kernel[0][0] = -1;
    kernel[0][1] = 0;
    kernel[0][2] = 1;
    kernel[1][0] = -1;
    kernel[1][1] = 0;
    kernel[1][2] = 1;
    kernel[2][0] = -1;
    kernel[2][1] = 0;
    kernel[2][2] = 1;
    Mat dstx = calcGrad(src, kernel, 3);
    int **kernel_1 = (int **) calloc(3, sizeof(int *));
    for (int i = 0; i < 3; i++)
        kernel_1[i] = (int *) calloc(3, sizeof(int));
    kernel_1[0][0] = 1;
    kernel_1[0][1] = 1;
    kernel_1[0][2] = 1;
    kernel_1[1][0] = 0;
    kernel_1[1][1] = 0;
    kernel_1[1][2] = 0;
    kernel_1[2][0] = -1;
    kernel_1[2][1] = -1;
    kernel_1[2][2] = -1;
    Mat dsty = calcGrad(src, kernel_1, 3);
    Mat modul = calcModul(dstx, dsty);
    Mat binar = convertFromGrayToWB_2(modul);
    Mat dir = calcDirectie(dstx, dsty, false);
    imshow("Src", src);
    imshow("Modul", modul);
    imshow("Binarizare", binar);

}

void calcSobel(Mat src, Mat *dst, Mat *dir) {
    int **kernel = (int **) calloc(3, sizeof(int *));
    for (int i = 0; i < 3; i++)
        kernel[i] = (int *) calloc(3, sizeof(int));
    kernel[0][0] = -1;
    kernel[0][1] = 0;
    kernel[0][2] = 1;
    kernel[1][0] = -2;
    kernel[1][1] = 0;
    kernel[1][2] = 2;
    kernel[2][0] = -1;
    kernel[2][1] = 0;
    kernel[2][2] = 1;
    Mat dstx = calcGrad(src, kernel, 3);
    int **kernel_1 = (int **) calloc(3, sizeof(int *));
    for (int i = 0; i < 3; i++)
        kernel_1[i] = (int *) calloc(3, sizeof(int));
    kernel_1[0][0] = 1;
    kernel_1[0][1] = 2;
    kernel_1[0][2] = 1;
    kernel_1[1][0] = 0;
    kernel_1[1][1] = 0;
    kernel_1[1][2] = 0;
    kernel_1[2][0] = -1;
    kernel_1[2][1] = -2;
    kernel_1[2][2] = -1;
    Mat dsty = calcGrad(src, kernel_1, 3);
    Mat modul = calcModul(dstx, dsty);
    //   Mat binar = convertFromGrayToWB_2(modul);
    *dst = modul;
    Mat dir1 = calcDirectie(dstx, dsty, false);
    *dir = dir1;

//    imshow("Src", src);
//    imshow("Modul", *dst);
//    imshow("Binarizare", binar);

}

void calcRoberts(Mat src) {
    int **kernel = (int **) calloc(2, sizeof(int *));
    for (int i = 0; i < 2; i++)
        kernel[i] = (int *) calloc(2, sizeof(int));
    kernel[0][0] = 1;
    kernel[0][1] = 0;
    kernel[1][0] = 0;
    kernel[1][1] = -1;
    Mat dstx = calcGrad(src, kernel, 2);
    int **kernel_1 = (int **) calloc(2, sizeof(int *));
    for (int i = 0; i < 3; i++)
        kernel_1[i] = (int *) calloc(2, sizeof(int));
    kernel_1[0][0] = 0;
    kernel_1[0][1] = -1;
    kernel_1[1][0] = 1;
    kernel_1[1][1] = 0;
    Mat dsty = calcGrad(src, kernel_1, 2);
    Mat modul = calcModul(dstx, dsty);
    Mat binar = convertFromGrayToWB_2(modul);

    Mat dir = calcDirectie(dstx, dsty, true);

    imshow("Src", src);
    imshow("Modul", modul);
    imshow("Binarizare", binar);
}


void calcModulAndDirec() {
    int type;
    printf("1.Prewitt\n");
    printf("2.Sobel\n");
    printf("3.Roberts\n");

    scanf("%d", &type);
    char fname[MAX_PATH];
    while (openFileDlg(fname)) {
        Mat src = imread(fname, IMREAD_GRAYSCALE);
        Mat dst = src.clone();
        Mat dir = src.clone();
        switch (type) {
            case 1:
                calcPrewit(src);
                break;
            case 2:
                calcSobel(src, &dst, &dir);
                break;
            case 3:
                calcRoberts(src);
                break;
        }

        waitKey(0);
    }
}

////EX4
Mat nonMaximelor(Mat src, Mat direction) {
    Mat dst = src.clone();
    for (int i = 1; i < src.rows - 1; i++) {
        for (int j = 1; j < src.cols - 1; j++) {
            switch (direction.at<uchar>(i, j)) {
                case 0:
                    if (src.at<uchar>(i, j) < src.at<uchar>(i - 1, j) ||
                        src.at<uchar>(i, j) < src.at<uchar>(i + 1, j))
                        dst.at<uchar>(i, j) = 0;
                    break;
                case 1:
                    if (src.at<uchar>(i, j) < src.at<uchar>(i - 1, j - 1) ||
                        src.at<uchar>(i, j) < src.at<uchar>(i + 1, j + 1))
                        dst.at<uchar>(i, j) = 0;
                    break;
                case 2:
                    if (src.at<uchar>(i, j) < src.at<uchar>(i, j - 1) ||
                        src.at<uchar>(i, j) < src.at<uchar>(i, j + 1))
                        dst.at<uchar>(i, j) = 0;
                    break;
                case 3:
                    if (src.at<uchar>(i, j) < src.at<uchar>(i - 1, j + 1) ||
                        src.at<uchar>(i, j) < src.at<uchar>(i + 1, j - 1))
                        dst.at<uchar>(i, j) = 0;
                    break;


            }
        }
    }
    return dst;

}

void cannyTest() {

    char fname[MAX_PATH];
    while (openFileDlg(fname)) {
        Mat src = imread(fname, IMREAD_GRAYSCALE);
        Mat gaussian = src.clone();
        Mat modul = src.clone();
        Mat direction = src.clone();
        Mat dst = src.clone();

        gaussian = filtruGaussianBi(src);

        calcSobel(gaussian, &modul, &direction);


        dst = nonMaximelor(modul, direction);

        imshow("Final", dst);


        waitKey(0);
    }

}

/////////////////////////////////////////////////////////LAB 12//////////////////////////////////////////////////////////////////////////////
/////////////////EX1
#define  WEAK 128
#define  STRONG 255

void calcBinHister(Mat *img, int pH, int pL) {


    for (int i = 0; i < img->rows; i++) {
        for (int j = 0; j < img->cols; j++) {
            if (img->at<uchar>(i, j) < pL)
                img->at<uchar>(i, j) = 0;
            else if (img->at<uchar>(i, j) > pH)
                img->at<uchar>(i, j) = STRONG;
            else
                img->at<uchar>(i, j) = WEAK;
        }
    }

}


void calcBinAdap(Mat img, int *pH, int *pL) {
    float p = 0.1f;
    float k = 0.4f;

    //1.calc histogram
    int *histogram = computeHistogram(img);
    //2.nr pixeli diferiti de zero
    float nrNonMuchie = (1 - p) * ((img.rows - 2) * (img.cols - 2) - histogram[0]);
    //3.
    int suma = 0;
    int adaptiveThreshold = 0;
    for (int i = 1; i < 256; i++) {
        suma += histogram[i];

        if (suma > nrNonMuchie) {
            adaptiveThreshold = i;
            break;
        }
    }

    *pH = adaptiveThreshold;
    *pL = k * adaptiveThreshold;
}

void calcAlgPrelungire(Mat *modul) {

    Mat_<uchar> visited = Mat::zeros(modul->size(), CV_8UC1);
    queue<Point> que;
    int height = modul->rows;
    int width = modul->cols;
    int dj[8] = {1, 1, 0, -1, -1, -1, 0, 1}; // row
    int di[8] = {0, -1, -1, -1, 0, 1, 1, 1}; // col

    for (int i = 2; i < height - 3; i++) {
        for (int j = 2; j < width - 3; j++) {
            if (modul->at<uchar>(i, j) == STRONG && visited(i, j) == 0) {
                que.push(Point(j, i));
                visited(i, j) = 1;
            }
            while (!que.empty()) {
                Point oldest = que.front();
                int jj = oldest.x;
                int ii = oldest.y;
                que.pop();
                int mag = 0;
                for (int d = 0; d < 8; d++) {
                    if (modul->at<uchar>(ii + di[d], jj + dj[d]) == WEAK) {
                        modul->at<uchar>(ii + di[d], jj + dj[d]) = STRONG;
                        que.push(Point(jj + dj[d], ii + di[d]));
                        visited(ii + di[d], jj + dj[d]) = 1;
                    }
                }
            }
        }
    }
    for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
            if (modul->at<uchar>(i, j) == WEAK) {
                modul->at<uchar>(i, j) = 0;
            }
        }
    }

}

void computeModulAndDirection(Mat temp, Mat *modul, Mat *directie) {
    int Sx[3][3] = {{-1, 0, 1},
                    {-2, 0, 2},
                    {-1, 0, 1}};
    int Sy[3][3] = {{-1, -2, -1},
                    {0,  0,  0},
                    {1,  2,  1}};
    int height = temp.rows;
    int width = temp.cols;


    for (int i = 3 / 2; i < height - 3 / 2; i++) {
        for (int j = 3 / 2; j < width - 3 / 2; j++) {
            int gradX = 0;
            int gradY = 0;
            for (int y = -3 / 2; y <= 3 / 2; y++) {
                for (int x = -3 / 2; x <= 3 / 2; x++) {
                    gradX += (Sx[y + 3 / 2][x + 3 / 2] * temp.at<uchar>(i + y, j + x));
                    gradY += (Sy[y + 3 / 2][x + 3 / 2] * temp.at<uchar>(i + y, j + x));
                }
            }
            (*modul).at<uchar>(i, j) = sqrt(gradX * gradX + gradY * gradY) / 5.65;
            int dir = 0;
            float teta = atan2((float) gradY, (float) gradX);
            if ((teta > 3 * PI / 8 && teta < 5 * PI / 8) || (teta > -5 * PI / 8 && teta < -3 * PI / 8))
                dir = 0;

            if ((teta > PI / 8 && teta < 3 * PI / 8) || (teta > -7 * PI / 8 && teta < -5 * PI / 8))
                dir = 1;

            if ((teta > -PI / 8 && teta < PI / 8) || teta > 7 * PI / 8 && teta < -7 * PI / 8)
                dir = 2;

            if ((teta > 5 * PI / 8 && teta < 7 * PI / 8) || (teta > -3 * PI / 8 && teta < -PI / 8))
                dir = 3;

            (*directie).at<uchar>(i, j) = dir;
        }
    }


}


void cannyFinal() {

    char fname[MAX_PATH];
    while (openFileDlg(fname)) {
        Mat src = imread(fname, IMREAD_GRAYSCALE);
        Mat gaussian = src.clone();
        Mat modul = Mat::zeros(src.size(), CV_8UC1);
        Mat direction = Mat::zeros(src.size(), CV_8UC1);


        imshow("Initial", src);
        gaussian = filtruGaussianBi(src);

        imshow("Gaussian", gaussian);

        computeModulAndDirection(gaussian, &modul, &direction);

        imshow("Modul sobel ", modul);


        Mat dst = Mat::zeros(modul.size(), CV_8UC1);

        dst = nonMaximelor(modul, direction);

        imshow("Modul after nonMaximum", dst);


        int pH, pL;
        calcBinAdap(dst, &pH, &pL);


        printf("PH %d", pH);
        printf("P%d", pL);

        calcBinHister(&dst, pH, pL);
        imshow("Binarizare adaptiva", dst);
        calcAlgPrelungire(&dst);
        imshow("Prelungire muchi", dst);


        waitKey(0);
    }

}

int main() {
    int op;
    do {
        system("cls");
        destroyAllWindows();
        printf("Menu:\n");
        printf(" 1 - Open image\n");
        printf(" 2 - Open BMP images from folder\n");
        printf(" 3 - Image negative - diblook style\n");
        printf(" 4 - BGR->HSV\n");
        printf(" 5 - Resize image\n");
        printf(" 6 - Canny edge detection\n");
        printf(" 7 - Edges in a video sequence\n");
        printf(" 8 - Snap frame from live video\n");
        printf(" 9 - Mouse callback demo\n");
        printf(" 10 - Negative image\n");
        printf(" 11 - Additive brightness\n");
        printf(" 12 - Multiplicative brightness\n");
        printf(" 13 - Cadrane\n");
        printf(" 14 - Inversa\n");
        printf(" 15 - Copy RGB\n");
        printf(" 16 - Convert from RGB to Gray Scale\n");
        printf(" 17 - Convert from  Gray Scale to WB\n");
        printf(" 18 - Convert from  RGB to HSV \n");
        printf(" 19 - Is inside?\n");
        printf(" 20 - Histogram\n");
        printf(" 21 - FDP\n");
        printf(" 22 - Print Histograme\n");
        printf(" 23 - Accumulator\n");
        printf(" 24 -Praguri multiple\n");
        printf(" 25 - Floyd-Steinbergr\n");
        printf(" 26 - Praguri multiple HSV \n");
        printf(" 27 - Lab4 ex1 \n");
        printf(" 28 - Lab4 ex2 \n");
        printf(" 29 - BFS \n");
        printf(" 30 - BFS two pass \n");
        printf(" 31 - Lab6 ex1 \n");
        printf(" 32 - Lab6 ex2 \n");
        printf(" 33 - Lab6 ex3 \n");
        printf(" 34 - Dilatare \n");
        printf(" 35 - Dilatare de n ori \n");
        printf(" 36 - Eroziune \n");
        printf(" 37 - Eroziune de n ori \n");
        printf(" 38 - Open Image\n");
        printf(" 39 - Open Image de n ori \n");
        printf(" 40 - Close Image\n");
        printf(" 41 - Close Image de n ori \n");
        printf(" 42 - Alg pt contur \n");
        printf(" 43 - Alg pentru umplere regiune\n");
        printf(" 44 - Histograma cumulativa\n");
        printf(" 45 - Binarizare\n");
        printf(" 46 - Negativul imagini\n");
        printf(" 47 - Modificare luminozitate\n");
        printf(" 48 - Modificare constrast\n");
        printf(" 49 - Corectie gamma\n");
        printf(" 50 - Egalizare histograma\n");
        printf(" 52 - Convolutie\n");
        printf(" 53 - Functii procesare\n");
        printf(" 54 - Eliminarea zgomotelor\n");
        printf(" 55 - Componente orizontale si verticale\n");
        printf(" 56 - Modulul si directia\n");
        printf(" 57 - Canny 1-3\n");
        printf(" 0 - Exit\n\n");

        printf("Option: ");
        scanf("%d", &op);

        switch (op) {
            case 1:
                testOpenImage();
                break;
            case 2:
                testOpenImagesFld();
                break;
            case 3:
                testParcurgereSimplaDiblookStyle(); //diblook style
                break;
            case 4:
                //testColor2Gray();
                testBGR2HSV();
                break;
            case 5:
                testResize();
                break;
            case 6:
                testCanny();
                break;
            case 7:
                testVideoSequence();
                break;
            case 8:
                testSnap();
                break;
            case 9:
                testMouseClick();
                break;
            case 10:
                testNegativeImage();
                break;
            case 11:
                printf("Write the additive: ");
                int additive;
                scanf("%d", &additive);
                brightnessAditive(additive);
                break;
            case 12:
                printf("Write the multiplicative: ");
                int multipliclative;
                scanf("%d", &multipliclative);
                brightnessMultiplicative(multipliclative);
                break;
            case 13:
                createMatrix_256();
                break;
            case 14:
                createMatrixFloat();
                break;
            case 15:
                copyRGB();
                break;
            case 16:
                convertFromRGBtoGray();
                break;
            case 17:
                printf("Write the threshold: ");
                int threshold;
                scanf("%d", &threshold);
                convertFromGrayToWB(threshold);
                break;
            case 18:
                convertFromRGBToHSV();

                break;

            case 19:
//                char fname[MAX_PATH];
//		        openFileDlg(fname);
//		        Mat img = imread(fname);
//                printf("Write i: ");
//                int i,j;
//                scanf("%d", &i);
//                printf("Write j: ");
//                scanf("%d", &j);
//                if(isInside(img, i, j)== true){
//                    printf("True");
//
//                }
//		        else{
//                    printf("False");
//                }
//                waitKey(5000);
                break;
            case 20:

                printVectorHistogram();
                break;
            case 21:
                printVectorFDP();
                break;
            case 22:
                printHistogram();
                break;
            case 23:
                printf("Write the nb of m: ");

                int m;
                scanf("%d", &m);
                printVectorNewHistogram(m);
                break;
            case 24:
                multilevelThresholding();
                break;
            case 25:
                floyd_steinberg();
                break;
            case 26:
                multilevelThresholdingHSV();

                break;
            case 27:

                testMouseClick3();
                break;
            case 28:

                char fname[MAX_PATH];
                while (openFileDlg(fname)) {
                    Mat src = imread(fname);
                    printf("Write the aria: ");
                    int aria;
                    scanf("%d", &aria);
                    printf("Write the phi low: ");
                    int low;
                    scanf("%d", &low);
                    printf("Write the phi high: ");
                    int high;
                    scanf("%d", &high);
                    lab4ex2(src, aria, low, high);
                    waitKey(0);
                    break;
                }
            case 29:
                showBfs();
            case 30:
                showAdancime2();
            case 31:
                trace_border(true);
            case 32:
                showDerivate();
            case 33:
                reconstruct();
            case 34:
                dilateN(false);
                break;
            case 35:

                dilateN(true);
                break;
            case 36:
                erosionN(false);
                break;
            case 37:
                erosionN(true);
                break;
            case 38:
                openImageN(false);
                break;

            case 39:
                openImageN(true);
                break;

            case 40:
                closeImageN(false);
                break;

            case 41:
                closeImageN(true);
                break;
            case 42:
                boundaryExtragery();
                break;

            case 43:
                fillRegion();
                break;
            case 44:
                cumulative_histogram();
                break;
            case 45:
                hist_binarizata();
                break;
            case 46:
                negativul_imagini();
                break;
            case 47:
                modificare_luminozitate();
                break;
            case 48:
                modificare_contrast();
                break;
            case 49:
                corectie_gamma();
                break;
            case 50:
                egalizare_histograma();
                break;
            case 51:
                afisare();
                break;
            case 52:
                filtruGen();
                break;
            case 53:
                frec_domain();
                break;
            case 54:
                alegere_lab10();
                //  biGaussianFilter();
                break;
            case 55:
                calcComp();
                break;
            case 56:
                calcModulAndDirec();
                break;
            case 57:
                cannyTest();
                break;
            case 58:
                cannyFinal();
                break;

        }


    } while (op != 0);
    return 0;
}
