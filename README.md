# FaceRecognition

## Directories
In the main directory the following folders should be found:

  - <i>src</i>: contains the source code. It is organized in packages divided accordingly to the functionalities the contained classes provide. The main to run is contained in the package "it.unipi.ing.mim.gui". The class "MenuFrame.java".

  - <i>data</i>: this directory contains all the data needed by the application to correctly behave. Due to the limited storage github provides, the different files need to be added manually.

  Such data are indicated below:

      1. The CNN: we use the caffe model of VGG-Face, that can be downloaded at http://www.robots.ox.ac.uk/~vgg/software/vgg_face/src/vgg_face_caffe.tar.gz,
      to be placed in the directory "data/vgg_face_caffe/"

      2. The image database: we use two versions of the lfw database, they can be both downloaded at
          - lfw http://vis-www.cs.umass.edu/lfw/lfw.tgz
          - lfw_funneled http://vis-www.cs.umass.edu/lfw/lfw-funneled.tgz

        to be placed respectively in the directories "data/lfw/" and "data/lfw_funneled"

      3. The file representing the images for the test: we use the same test set as the one provided at http://vis-www.cs.umass.edu/lfw/pairsDevTest.txt,
      to be saved as "data/pairsDevTest.txt"

      4. The haarcascades model for the openCV libraries, provided here https://github.com/opencv/opencv/tree/master/data/haarcascades,
      to be placed in "data/haarcascades/"

      5. The descriptors of the images of the database lfw_funneled: this file can be created by running the method "CreateFeaturesFile", but due to
          timing reason we decided to make it available at the following link: https://drive.google.com/open?id=0B3GDol5zeu2Zbkg0aWRWRVdWSUk,
          to be placed in "data/seqFeaturesFile.dat"

  - <i>lib</i>: contains the needed libraries. Such libraries are:
                javacpp, javacv, opencv

  - <i>out</i>: contains all the file created by the application. If no execution has already been done the directory should not exist.
