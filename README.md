# vfl-app

### *-TR-

#### Açıklama:

* VFL-App; Validebağ Fen Lisesi için geliştirilmiş açık kaynaklı bir mobil uygulamadır. Lise içindeki iletişimin ortak bir platformda toplanması ve diğer projeler ile bağlantı kurabilmek için oluşturulmuştur. Bu repository uygulamanın Android sürümünü kapsar. 

#### Amaç:

* Lise içindeki iletişimin ortak bir platformda toplanması:
  - Duyuruların okulun kendi kontrol edebileceği bir platformda olmasını sağlamak.
  - Veli ve öğrencilerden gelen soruların kısa sürede cevaplanmasını sağlamak.
* Diğer projeler ile bağlantı kurmak:
  - Okul için eğitici bir kriptoloji oyunu geliştirmek istiyorsunuz. Oyun şemalarınızı, kurallarınızı hazırladınız. Bunu cihazlardan erişilebilir hale getirmek istiyorsunuz. İster internet sitesine koyun, isterseniz de teorik halde sunun. Fikriniz onaylanırsa yeni bir uygulama oluşturma zahmetinden ve maliyetinden kaçınıp projenizi uygulamaya uygulayabiliriz.

#### Teknik Detaylar:

* Geliştirilen ortam Android Studio'dur.
* Programlama yaparken kullanılacak dil kesinlikle ingilizcedir; Değişken adları, fonksiyon adları, dosyalar, package'ler, id'ler vs.
* Commit mesajlarınızda İngilizce kullanılır. İngilizceyi kullanmamızın sebebi teknik detayların yoğunluklu olarak ingilizce kelimelerden oluşmasıdır. 
* İstemci için programlama dili Kotlin'dir. Java da kabul edilir ama incelenme süresi daha uzun ve kabul edilmesi Kotlin'e göre daha zorludur. Neyse ki, Java kodunuzu Kotlin kodunuza da dönüştürebilirsiniz
* Sunucu tarafında Firebase kullanılır.
* İstemcinin yapamadığı eylemler Firebase Cloud Functions servisi ile yapılır. Buradaki programlama dili Javascript'tir, çalışma motoru ise Node.js'dir.

### *-En-

#### Overview

* VFL-App is an open source mobile application created for Validebağ Science High School. It is created to gather communication in high school on a common platform and make a connection with other projects. This repository involves Android version of app.

#### Aim

* Gathering communication in high school on a common platform:
  - To provide announcements on a platform which can be controlled by school.
  - To provide questions that from parents and students are answered in a short period of time.
* Making a connection with other projects:
  - Suppose that, you want to create a educational cryptology game for school. You prepared game schemas and rules. You want to make it accessible from devices. Whether you put it on website or present it theoratically. If your idea get approved, you can avoid the trouble and cost of creating a new application and we can apply your project into the application. 

#### Technical Details

* Development Environment is Android Studio.
* The language is definetly English while programming. Such as; variable names, function names, files, packages, id's etc.
* The language is English commit messages. The reason why we use English is most technical details are English.
* The programming language for client is Kotlin. You can use Java too but review period is requires longer time and change of approvation is less than Kotlin. Fortunately, you could convert your Java to Kotlin code.
*  Firebase is used for server side.
*  The actions that client couldn't are made by Firebase Cloud Functions service. The programming language for it is Javascript and runtime engine is Node.js.
