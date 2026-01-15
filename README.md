# AndroidArchitectureTemplate
<!-- このアプリの概要を書いてください -->

# Feature
<!-- このアプリでできることを書いてください -->

## Screenshots

# Architecture


# Setup Development Environment
## 1. Install Android Studio
最新のstableバージョンのAndroid Studioをインストールしてください  
https://developer.android.com/studio

## 2. Use this template
新しくアプリケーションを作成する場合は、このテンプレートリポジトリを使って新しくGithubリポジトリを作成してください。

## 3. Setup Github repository settings
### Branch rulesets
[Githubドキュメント](https://docs.github.com/en/repositories/configuring-branches-and-merges-in-your-repository/managing-rulesets/managing-rulesets-for-a-repository#deleting-a-ruleset)に従ってBranchの保護設定を行ってください。

**Recommended setting**
- Restrict deletions
- Require a pull request before merging
- Require status checks to pass
- Block force pushes

### Pull Requests setting
**Recommended setting**
- Always suggest updating pull request branches
- Allow auto-merge
- Automatically delete head branches

## 4. Run customizer.sh
applicationIdを任意のものに置き換える `customizer.sh` を実行してください。

```sh
$ ./customizer.sh
```

## 5. Setup Firebase project
1. [Firebaseプロジェクトをセットアップ](https://firebase.google.com/docs/android/setup?hl=ja)
2. `app/google-services.json`を上書きする
  a. 複数のビルドタイプやプロダクトフレーバーを持つ場合は[こちら](https://developers.google.com/android/guides/google-services-plugin?hl=ja#adding_the_json_file)を参考にしてください
3. [こちら](https://firebase.google.com/docs/cli?hl=ja)に従って、`FIREBASE_TOKEN`を取得する
4. `FIREBASE_TOKEN`をGithub Actionsのsecretsに登録する
