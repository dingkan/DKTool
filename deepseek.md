### 准备
    1。通过docker部署ollama、postgresql、redis环境
     docker-compose  -f docker-compose-environment.yml up -d
    2。通过ollama下载部署deepseek
        对话模型    ollama pull deepseek-r1:1.5b 
        运行模型    ollama run deepseek-r1:1.5b
        联网模型    ollama pull nomic-embed-text    
    3.maven使用3.8 分别创建三模块 （api 定义接口、app 实现接口、trigger 配置 启动）


###项目
##### 引入 Spring AI 框架组件，对接 Ollama DeepSeek 提供服务接口
    1.SpringAI是为了AI集成的基本挑战，将企业数据与API与AI模型联系起来
    2.Spring AI 支持；OpenAI，Microsoft，Amazon，Google和Ollama，大模型的对接。其他不属于这个范围的
    3.SpringAI 提供的接口 call直接应答。stream流水应答