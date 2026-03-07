# 🚀 快速开始

## 代码指南

CuteApps 目前没有很多严格的规范，但最好遵循现有的一些基本原则，例如：

-   **简洁无杂乱的体验**：这一点很好理解，避免添加不必要的元素。两个示例：

    -   **闪烁的音乐图标**：搜索栏中的音乐图标会闪烁红色，表示它是可点击的
        / 与某个操作相关
    -   **重启按钮**：在歌曲播放到 10
        秒后，"返回上一首"按钮会自动变成"回退 10
        秒"，而不是新增一个完全不同的按钮。这也引出了下一条原则：

-   **让功能清晰易懂**：如果你添加一个功能，请确保用户能清楚理解它的作用，例如：清晰的文本说明、准确的图标。

-   **横屏模式**：CuteApps **必须**
    完全兼容横屏模式。如果某个竖屏设计无法很好适配横屏，你需要专门设计一个横屏布局。

-   **创意**：这不是强制要求，但如果你在设计界面或其他内容，请尽量发挥创意！尝试做一些其他应用没有做过的设计，让应用保持独特。记住：失败只是离完美更近的一步。

## 前置要求

-   Android Studio（建议使用最新版本）
-   Java Development Kit (JDK) 11 或更高版本
-   Git

## 安装步骤

1.  **克隆仓库：**

``` bash
git clone https://github.com/sosauce/CuteMusic.git
cd CuteMusic
```

2.  **在 Android Studio 中打开项目：**

-   打开 Android Studio
-   选择 `Open an existing project`
-   找到 `CuteMusic` 目录并选择它

3.  **构建项目：**

-   在顶部菜单点击 `Build`
-   选择 `Make Project` 并确保没有报错

4.  **运行应用：**

-   连接 Android 设备或使用模拟器
-   在顶部菜单点击 `Run`
-   选择你的设备并点击 `OK`

## 贡献代码

1.  **Fork 仓库：**

-   在仓库页面右上角点击 `Fork`

2.  **创建新分支：**

``` bash
git checkout -b feature/YourFeatureName
```

3.  **进行修改：**

-   实现你的功能或修复 bug
-   确保代码符合项目的编码规范

4.  **提交更改：**

``` bash
git add .
git commit -m "Add feature: YourFeatureName"
```

5.  **推送到你的 Fork：**

``` bash
git push origin feature/YourFeatureName
```

6.  **创建 Pull Request：**

-   前往原始仓库
-   点击 `Pull Requests` → `New Pull Request`
-   选择你的分支并提交 Pull Request

感谢所有愿意花时间为应用做出贡献并改进它的人 ❤️！！！
