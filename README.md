# aws-s3image-server
A Spring boot AWS S3 image handler server app

Steps:

* Sign into aws console and create a bucket ,name it as "tecky-image-upload".
(Note: if you want someother name, change name in profileimage at src/main/java/com/tecky/awsimageupload/bucket/BucketName.java).

* Configure aws region and authentication in src/main/java/com/tecky/awsimageupload/config/AmazonConfig.java

* In this example im using static users data from src/main/java/com/tecky/awsimageupload/datastore/FakeUserProfileDataStore.java

* Modify the code as per your needs.

* Deploy the code and use postman or API platform of your choice.


You can use our react client app also:
> https://github.com/anandh-tecky/awss3reactclient


Happy Coding....!
