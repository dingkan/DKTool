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

##### Ollama RAG 知识库上传、解析和验证
    目标：以大模型向量存储的方式，提交本地文件到知识库。并在AI对话中增强检索知识库符合AI对话内容的资料，合并提交问题
    技术方案：以SpringAI提供的向量模型处理框架，将上传文件以TikaDocumentReader方式进行解析，再通过TikaDocumentReader拆分文件。
            并将拆解后的文件存储到PostgreSQL向量库中
    SpringAI：提供向量模型处理框架、支持文件的解析、拆分和向量化操作
    TikaDocumentReader： 解析上传文件
    TokenTextSplitter： 将解析后的文本内容拆分为更小的片段
    PostgreSQL： 存储向量化后的文件


            