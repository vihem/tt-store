content ���ݹ���ϵͳ

	��ҳ�ĸ��ֹ��λ���ֲ�ͼ����

����dao��pojo��ֻ��Ҫ����manager��dao��pojo

taotao-content-interface �ӿڲ㣨����manager-pojo��

taotao-content-service ����㣨����manager-dao�������ʽwar
	
	ע�����ĵĵ�ַ��dubbo�Ķ˿ں���Ҫ�޸ģ�20881������Ϊmanager��ռ��һ��20880
	
����web.xml�������ֻ��Ҫ��ʼ��spring����

�����ϣ�jar���ǲ��ܰ���jar�������Դ����war����ֱ�Ӳ���tomcat�����㲿��

���ݷ������

��manager-dao�е� TbContentCategoryMapper �� id="insert"��id="insertSelective" �����������룺

	<!-- SELECT LAST_INSERT_ID()�����Բ鵽�������id�� 
		keyProperty="id"������com.taotao.pojo.TbContentCategory��id���ԣ��Ѳ�ѯ���������id���ԣ�Ҳ����tb_content_category�������
		resultType="long"��SELECT LAST_INSERT_ID()ִ����ɷ�������Ϊ��TbContentCategory�У�Long id;
		order="AFTER"���ڲ������ִ��֮����иò�ѯ
	-->
	<selectKey keyProperty="id" resultType="long" order="AFTER">
		SELECT LAST_INSERT_ID()
	</selectKey>

������ݷ���֮����Կ�F12������ģʽ-Network-Header-Form Data.
--------
��ҳʹ��redis����

	1. ���룺redis-3.0.0.tar.gz ��һ��c���Կ�����Դ���룬��Ҫ���б��롣����Դ��Ŀ¼����Makefile�ļ��������ڵ�ǰĿ¼��ִ��make������б���
	2. ��װ��make install PREFIX=/usr/local/reids  -- PREFIX=��װĿ¼
	3. ǰ������ģʽ������/usr/local/reids/binĿ¼   ./redis-server
	4. �������ģʽ�����������ļ���cp ~/redis-3.0.0/redis.conf /usr/local/reids/bin ���޸ģ�daemonize yes����ǰĿ¼��������./redis-server redis.conf
	5. �鿴�Ƿ��Ѿ�������ps aux|grep redis
	6. ����redis����/usr/local/reids/bin #./redis-cli #ping 
		# ./redis-cli -h 192.168.25.133 -p 6379
	
	redis ��ŵĶ����ַ��������Ǽ�ֵ��
	
redis�־û�

���ַ�������ͬʱ����
	
	RDB(����)��ʽ��Ĭ�ϣ������ڸ��£�������ڴ��У�����Ϊ���ա�dump.rdb�ļ��������ļ�����
	AOF��ʽ��������Ƕ����ݿ�������������������һ���½�����Ϊ����Ƶ���������̣����������Խϸߡ�

redis��Ⱥ

1. ����6��redis�����ڵ㣺

	port 7001~7006
	cluster-enabled yes������Ⱥ������ǰ��һ����Ⱥģʽ
	#cd /usr/local/redis-cluster/
	��ӿ�ִ��Ȩ�ޣ� chmod +x start-all.sh
	����#./start-all.sh
	�鿴�Ƿ�������ps aux|grep redis

2. ��װruby
	
	yum install ruby
	gem install redis-3.0.0.gem ��װruby����������
	cp /root/redis-3.0.0/src/redis-trib.rb /usr/local/redis-cluster
	
3. ���Ⱥ��

	# cd /usr/local/redis-cluster
	# ./redis-trib.rb create --replicas 1 192.168.25.135:7001 192.168.25.135:7002 192.168.25.135:7003 192.168.25.135:7004 192.168.25.135:7005 192.168.25.135:7006
	--replicas 1 ��1��ʵ�������һ���������������ڵ���/�ӽڵ����ı�������ô��һ�룬�ڴ�����Ⱥ��ʱ����Щ�ڵ������ڵ��أ���Щ�ڵ��Ǵӽڵ��أ����ǽ�����������IP:PORT��˳������3�����ڵ㣬Ȼ����3���ӽڵ㡣
	���Ӽ�Ⱥ��redis01/redis-cli -p 7006 -c 
	192.168.25.135:7006> cluster info
	
	
	