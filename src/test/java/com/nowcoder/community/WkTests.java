package com.nowcoder.community;

import org.junit.Test;

import java.io.IOException;

public class WkTests {

    @Test
    public void WkTest1(){
        String cmd = "E:/installforjava/wkhtmltopdf/bin/wkhtmltoimage --quality 75 https://mp.weixin.qq.com/s?__biz=MzUxODAzNDg4NQ==&mid=2247490008&idx=1&sn=8f576e69ec63e02a8b42a00ae6754f0a&chksm=f98e5d72cef9d464710c891c4c0537c20e4949b39ee70c97c44c3f6f95df83fc406f52fc161b&mpshare=1&scene=1&srcid=0323fLVbIIuHevDEkbBy8e9D&sharer_sharetime=1616457633198&sharer_shareid=f62f62a3743c548631b2add2d57c0680&key=37c5ac0d7f3e09d8fd8b27cb95b589d7fcc879f5ec6fd398707a2b0f0f84bdcca8b17e6dbec5d8fa3702e05408ac881e1c331f238a54014134aaaa1c6d1aa78a263a493a1663239a260845bb0cc9e65a018232b1235206b594e90022d3df0bfdafb0a94f85ffcddf6b63ebca991f0117b5117a75e3739758ecfe3c4a9963bc97&ascene=1&uin=MjAzMjI5NTkwMQ%3D%3D&devicetype=Windows+10+x64&version=63000039&lang=zh_CN&exportkey=AY%2BgRZw5P43cTR3H%2BMd87DY%3D&pass_ticket=cra7jGlZiCckVuQQuD2uMYQ3sI0CHhb2eKUN2D2Acn44cX%2Fl2iFVh9Ha7DPxuZml&wx_header=0 e:/data/wk-images/redis.png";
        try {
            Runtime.getRuntime().exec(cmd);
            System.out.println("ok");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
