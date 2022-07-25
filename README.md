# CustomEditText

### 自定义EditText

[![](https://jitpack.io/v/FPhoenixCorneaE/CustomEditText.svg)](https://jitpack.io/#FPhoenixCorneaE/CustomEditText)

![](https://github.com/FPhoenixCorneaE/CustomEditText/blob/main/images/pic_custom_edit_text.png)

### How to include it in your project:

**Step 1.** Add the JitPack repository to your build file.

```groovy
allprojects {
    repositories {
        google()
        mavenCentral()
        maven {
            url "https://jitpack.io"
        }
    }
}
```

**Step 2.** Add the dependency.

```groovy
dependencies {
    implementation("com.github.FPhoenixCorneaE:CustomEditText:$latest")
}
```

Or add the dependencies you need

```groovy
dependencies {
    implementation("com.github.FPhoenixCorneaE.CustomEditText:separatorEditText:$latest")
    implementation("com.github.FPhoenixCorneaE.CustomEditText:prefixEditText:$latest")
    implementation("com.github.FPhoenixCorneaE.CustomEditText:emojiDisableEditText:$latest")
    implementation("com.github.FPhoenixCorneaE.CustomEditText:passwordEditText:$latest")
    implementation("com.github.FPhoenixCorneaE.CustomEditText:regexEditText:$latest")
    implementation("com.github.FPhoenixCorneaE.CustomEditText:suffixEditText:$latest")
}
```

### 特性

* ##### PrefixEditText：添加前缀

* ##### SuffixEditText：添加后缀

* ##### EmojiDisableEditText：禁止输入表情符与特殊符号

* ##### RegexEditText：输入内容根据正则是否拦截

* ##### PasswordEditText：密码输入框, 可切换显示/隐藏状态, 可清除

* ##### SeparatorEditText：添加分隔符
