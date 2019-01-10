# SnapShop
This is an image editor I wrote as a programming assignment for TCSS 305 at University of Washington Tacoma in Spring of 2017. It applies some filters (which were provided by the instructor) to images that are loaded into the Java Swing GUI which I wrote and which can be saved to the users disk. 

# Running SnapShop:
## Launching SnapShop:
    Compile the project and run the SnapShopMain.java file. This will launch the GUI for the program and allow you to 
    open an image. 
## Opening an image in SnapShop:
    Clicking the "Open" button in the bottom left of the window will open a file explorer in the SnapShop folder. From
    there you can select and load an image into the GUI. (The "sample_images" folder contains several images of 
    various types to test the program with.) 
## Editing an image in SnapShop:
    Once you have loaded an image into the SnapShop GUI you can apply filters from the left hand tool bar to the image.
    Keep in mind that only the flip filters are reversable. All other filters can not be removed once applied. 
## Saving an image in SnapShop:
    Once you have edited a picture in SnapShop you can save the image for future use. To Do this first click the 
    "Save As" button in the bottom left of the window. From here you can either overwrite the existing image  by 
    clicking save or you can give the image a new name by typing it into the "File Name" field of the file explorer 
    and then clicking save. 
    From here you can either continue to edit the image by applying more filters to it, load a new image via the 
    "Open" button, close the current image via the "Close Image" button, or exit the program with the x in the corner 
    of the window. 
    
# package outline:
## filters:
    Contains the filters which were provided by the instructor for my program to apply to the images. 
## gui:
    Contains the code for the GUI and a main file to launch the program.
## image:
    Contains the model used for images. 
## sample_images: 
    Contains a hand full of images of various file types provided by the instructor for testing purposes.

