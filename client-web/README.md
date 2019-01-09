# Timeline Web Client
## 介绍

本项目是项目时间线的网页端，用户可以进行以下操作

- 登陆/注册

- 查看最近消息/下一页

- 发送消息（可以带图片）

## 主要依赖
- 前端组件框架 `React`
- css框架 `React-Bootstrap`
- 数据流框架 `Redux`
- `moment.js` 
- Http client `superagent`

## 快速启动

你需要先安装NodeJs进行开发

```bash
npm install
# 或 yarn
npm run start
```

## FAQ

项目是否进行静态代码检测？

是的，本项目使用 `Eslint` 工具进行检测，使用 `Airbnb` 风格。同时还使用 `prettier` 作为代码格式化工具。

可以通过以下命令，进行代码静态测试。

```bash
eslint -c package.json **/*.js[x] --ignore-pattern node_modules/
```



