# hsbcdemo
Simple HSBC demo
Language: Kotlin.
• Use Jetpack as your Tech Stack, 
• Use popular open-source libs as much as possible.
• MinSdkVersion 23.
• Api is already offered by github.
• Apps need storage permission. should have permission dialog.
• use Design Model to replace easily in case of we change Image implementation in future. In the app. 
• base page which can be used to other pages. 
• assistant tool integrated to your project at least. more is a plus.
本项目临时开放，所以起的名称后续会更改，动态皮肤采用自定义fractory2
项目采用 MVVM架构，基于Hilt 完成各层注入，全局提供了Okhttp、retrofit +协程的网络框架，模拟了登录操作操作，登录后通过个人中心退出按钮，退出登录，
首页借助youtube开放资源，完成最新热门短视频查询，后续根据接口完成播放器的接入，首页分类查询后续完善，由于时间原因，细节功能还没完善，但后续本人会依次补齐。


