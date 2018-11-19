# Android_Demo   butterknif 还没解决好
java转kotlin
测试android 的Demo
暂时包括的内容
1.activity基类  fragment基类   presenter基类  contact基类
2.eth的钱包生成
3.首页展示页面 空白
4.引入包如下
    implementation 'com.android.support:appcompat-v7:28.0.0'
    implementation 'com.android.support:design:28.0.0'
    implementation 'com.android.support.constraint:constraint-layout:1.1.3'
    testImplementation 'junit:junit:4.12'

    //rx
    implementation 'com.squareup.retrofit2:retrofit:2.4.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.4.0'
    implementation 'com.squareup.retrofit2:adapter-rxjava:2.4.0'
    implementation 'io.reactivex:rxandroid:1.2.1'
    implementation 'io.reactivex.rxjava2:rxandroid:2.0.2'

    //ethereum  钱包
    implementation 'org.web3j:core:3.5.0'
    implementation 'org.bitcoinj:bitcoinj-core:0.14.3'

    //butter knife
    implementation 'com.jakewharton:butterknife:8.8.1'
    kapt 'com.jakewharton:butterknife:8.8.1'
    annotationProcessor 'com.jakewharton:butterknife-compiler:8.8.1'
    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version"

    //event bus
    implementation 'org.greenrobot:eventbus:3.1.1'
