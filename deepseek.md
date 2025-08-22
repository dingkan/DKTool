### 准备
    1。通过docker部署ollama、postgresql、redis环境
     docker-compose  -f docker-compose-environment.yml up -d
    2。通过ollama下载部署deepseek
        对话模型    ollama pull deepseek-r1:1.5b 
        运行模型    ollama run deepseek-r1:1.5b
        联网模型    ollama pull nomic-embed-text    
    3.maven使用3.8 分别创建三模块 （api 定义接口、app 实现接口、trigger 配置 启动）


    