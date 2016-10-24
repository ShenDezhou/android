##项目简介
使用spring-boot搭建服务端应用,理解spring消除样板式编码的思想

##集成
项目当前集成了日志框架log4j2,基于springMVC构建了一个简单的Restful风格站点.

##包含功能
- 响应HTTP GET请求
- 响应HTTP POST请求
- 文件上传

##如何使用
###环境配置
- Java7或以上
- maven3.3或以上

###如何运行
1. 进入项目根目录,执行`mvn spring-boot:run`,在控制台可看到启动成功标志.
2. 项目启动后,浏览器访问 `http://127.0.0.1:9999/` 成功返回 `Hello spring-boot`即成功.

##接口列表
### 请求编码类型
采用`application/json`编码格式 
前端请求时,需要设置请求头信息`Content-Type:application/json`

###1.查看用户信息
####接口URL
/user/{userid} 
userid为用户id,例如,需要查看用户id为2的详细信息,则请求`/user/2`
####请求参数
| 参数名        | 类型           | 说明  |
| ------ |:------:| ---:|
| 无                | 无               | 无      |
####请求方法 
**GET** 
####返回值
| 字段名        | 类型           | 说明  |
| ------ |:------:| ---:|
| id     | long   | 用户id |
| username | string   | 用户名 |
---
#####示例
```
{
    "code": "SUCCESS",
    "data": {
        "id": 1001,
        "username": "rockrong"
    },
    "description": null
}
```

###2.登陆接口
####接口URL
/user/login
####请求参数
| 参数名        | 类型           | 说明  |
| ------ |:------:| ---:|
| username    | String               | 用户名      |
| password    | String               | 密码      |
####请求方法 
**POST** 
####返回值
| 字段名        | 类型           | 说明  |
| ------ |:------:| ---:|
| id     | long   | 用户id |
| username | string   | 用户名 |
---
#####示例
```
{
    "code": "SUCCESS",
    "data": {
        "id": 1001,
        "username": "rockrong"
    },
    "description": null
}
```

###2 用户列表接口
####接口URL
/user/list
####请求参数
| 参数名        | 类型       |是否必填    | 说明  |
| ------ |:------:| ---:|---|
| currPage    | Integer               |否     |当前页,默认为`1`|
| pageSize    | Integer               | 否    |当前页显示大小,默认为`10`|
####请求方法 
**POST** 
####返回值
| 字段名        | 类型           | 说明  |
| ------ |:------:| ---:|
| id     | long   | 用户id |
| username | string   | 用户名 |
---

#####本地环境
http://127.0.0.1:9999/user/list?currPage=1&pageSize=5

#####返回JSON示例
```
{
    "code": "SUCCESS",
    "data": [
        {
            "id": 1001,
            "username": "rockrong1"
        },
        {
            "id": 1002,
            "username": "rockrong2"
        },
        {
            "id": 1003,
            "username": "rockrong3"
        },
        {
            "id": 1004,
            "username": "rockrong4"
        },
        {
            "id": 1005,
            "username": "rockrong5"
        }
    ],
    "description": null,
    "page": {
        "totalCount": 12, --总记录数
        "totalPage": 3, --总页数
        "currPage": 1, --当前页
        "count": 5, --当前页记录数量
        "pageSize": 5, --当前页显示数量
        "sort": null, --排序值,暂未提供
        "next": true --是否还有下一页
    }
}
```
##项目简介
使用spring-boot搭建服务端应用,理解spring消除样板式编码的思想

##集成
项目当前集成了日志框架log4j2,基于springMVC构建了一个简单的Restful风格站点.

##包含功能
- 响应HTTP GET请求
- 响应HTTP POST请求
- 文件上传

##如何使用
###环境配置
- Java7或以上
- maven3.3或以上

###如何运行
1. 进入项目根目录,执行`mvn spring-boot:run`,在控制台可看到启动成功标志.
2. 项目启动后,浏览器访问 `http://127.0.0.1:9999/` 成功返回 `Hello spring-boot`即成功.

##接口列表
### 请求编码类型
采用`application/json`编码格式 
前端请求时,需要设置请求头信息`Content-Type:application/json`

###1.查看用户信息
####接口URL
/user/{userid} 
userid为用户id,例如,需要查看用户id为2的详细信息,则请求`/user/2`
####请求参数
| 参数名        | 类型           | 说明  |
| ------ |:------:| ---:|
| 无                | 无               | 无      |
####请求方法 
**GET** 
####返回值
| 字段名        | 类型           | 说明  |
| ------ |:------:| ---:|
| id     | long   | 用户id |
| username | string   | 用户名 |
---
#####示例
```
{
    "code": "SUCCESS",
    "data": {
        "id": 1001,
        "username": "rockrong"
    },
    "description": null
}
```

###2.登陆接口
####接口URL
/user/login
####请求参数
| 参数名        | 类型           | 说明  |
| ------ |:------:| ---:|
| username    | String               | 用户名      |
| password    | String               | 密码      |
####请求方法 
**POST** 
####返回值
| 字段名        | 类型           | 说明  |
| ------ |:------:| ---:|
| id     | long   | 用户id |
| username | string   | 用户名 |
---
#####示例
```
{
    "code": "SUCCESS",
    "data": {
        "id": 1001,
        "username": "rockrong"
    },
    "description": null
}
```

###2 用户列表接口
####接口URL
/user/list
####请求参数
| 参数名        | 类型       |是否必填    | 说明  |
| ------ |:------:| ---:|---|
| currPage    | Integer               |否     |当前页,默认为`1`|
| pageSize    | Integer               | 否    |当前页显示大小,默认为`10`|
####请求方法 
**POST** 
####返回值
| 字段名        | 类型           | 说明  |
| ------ |:------:| ---:|
| id     | long   | 用户id |
| username | string   | 用户名 |
---

#####本地环境
http://127.0.0.1:9999/user/list?currPage=1&pageSize=5

#####返回JSON示例
```
{
    "code": "SUCCESS",
    "data": [
        {
            "id": 1001,
            "username": "rockrong1"
        },
        {
            "id": 1002,
            "username": "rockrong2"
        },
        {
            "id": 1003,
            "username": "rockrong3"
        },
        {
            "id": 1004,
            "username": "rockrong4"
        },
        {
            "id": 1005,
            "username": "rockrong5"
        }
    ],
    "description": null,
    "page": {
        "totalCount": 12, --总记录数
        "totalPage": 3, --总页数
        "currPage": 1, --当前页
        "count": 5, --当前页记录数量
        "pageSize": 5, --当前页显示数量
        "sort": null, --排序值,暂未提供
        "next": true --是否还有下一页
    }
}
```
