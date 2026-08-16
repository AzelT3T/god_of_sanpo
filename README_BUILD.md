# ビルド手順 (Windows)

前提:
- JDK 11 以上がインストールされ、`java` が PATH にあること
- Android SDK / Android Studio がインストールされていること
- 環境変数 `ANDROID_HOME` または `ANDROID_SDK_ROOT` が設定されていること

簡単ビルド:
```bat
cd "c:\趣味開発\god_of_sanpo"
build_debug.bat
```

手動でGradleラッパーを使う場合:
```bat
cd "c:\趣味開発\god_of_sanpo"
gradlew.bat assembleDebug
```

注意:
- 初回実行時はGradleディストリビューションをダウンロードします。
- ビルドに失敗した場合は、`java -version` と Android SDK のパスを確認してください。
