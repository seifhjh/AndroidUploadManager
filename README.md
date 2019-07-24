[![N|Solid](https://seifhadjhassen.net/img/banneraum.png)](https://seifhadjhassen.net/img/banneraum.png)

# Android Upload Manager
[![](https://jitpack.io/v/seifhjh/AndroidUploadManager.svg)](https://jitpack.io/#seifhjh/AndroidUploadManager)
# ScreenShot
[![N|Solid](https://seifhadjhassen.net/img/upmanager.gif)](https://seifhadjhassen.net/img/upmanager.gif)
# Download
##### Step 1. Add the JitPack repository to your build file 
```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```
##### Step 2. Add the dependency
```
dependencies {
implementation 'com.github.seifhjh:AndroidUploadManager:1.2.0'
}
```
# How to use
### Client (Android)
``` java
 @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        UploadManager.urlServer="http://host/uploadFile";
    }
```

#### One file
``` java
 UploadManager.uploadFileFromFile(getApplicationContext(), file, new UploadManager.OnProgressListener() {
            @Override
            public void onProgress(long percent) {
                Log.e(TAG,"onProgress : "+percent);
            }

            @Override
            public void onComplite(String response) {
                Log.e(TAG,"onComplite : "+response);
            }

            @Override
            public void onFailed(Throwable error) {
                Log.e(TAG,"onFailed : "+error.getMessage());
            }
        });
```
#### Multi files
``` java
  List<File> listFiles=new ArrayList<>();
        listFiles.add(file);
        listFiles.add(file);
        UploadManager.uploadFileFromFile(getApplicationContext(), listFiles, new UploadManager.OnProgressMultiListener() {
            @Override
            public void onProgress(long percent) {
                Log.e(TAG,"onProgress : "+percent);
            }

            @Override
            public void onComplite(List<String> listResponse) {
                Log.e(TAG,"onComplite : "+listResponse);
            }

            @Override
            public void onFailed(Throwable error) {
                Log.e(TAG,"onFailed : "+error.getMessage());
            }
        });
```
### Server
``` php
<?php
include "conn.php";
try{
    $name = time();
    $path = $_FILES['file']['name'];
    
    $ext = pathinfo($path, PATHINFO_EXTENSION);
    $stored_name ="file-$name.$ext";
    if (!file_exists('path/file')) {
        mkdir('path/file/', 0777, true);
        }
        
    if(move_uploaded_file($_FILES['file']['tmp_name'],"path/file/$stored_name"))
    {
        mime_content_type('path/file/'.$stored_name);

        $result=array(
            'master'=>array(
            'etat' => "succes",
            'url'=> "$host/path/file/$stored_name"
            ));
    }else{
        $result=array(
            'master'=>array(
            'etat' => "failure",          
            'url'=> "$host/path/file/profil-default.jpg"
            ));
    }

    echo json_encode($result);

}catch(Exception $e){
  
}		

?>
```
### Output
``` json
{
"master":
{"etat":"succes","url":"http:\/\/host\/path\/file\/file-1563922243.mp4"}
}
```
## Full Example
[MainActivity.java](https://github.com/seifhjh/AndroidUploadManager/blob/master/app/src/main/java/net/seifhadjhassen/androiduploadmanager/MainActivity.java)


# Developer
[Seif Hadjhassen - Github](https://github.com/seifhjh)\
[Seif Hadjhassen - Linked In](https://www.linkedin.com/in/seifhadjhassen)\
[Seif Hadjhassen - Facebook](https://www.facebook.com/seif.hajhassen)\
[Seif Hadjhassen - Twitter](https://twitter.com/seifhadjhassen)\
[Seif Hadjhassen - Dribbble](https://dribbble.com/seifhadjhassen)\
[Seif Hadjhassen - Pinterest](https://www.pinterest.com/seifhadjhassen)
# License
```
  Copyright 2019 Seif Hadjhassen

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
