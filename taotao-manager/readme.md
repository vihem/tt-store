taotao-manager��
���Ƿ���㹤�̡��ۺϹ��̡������ʽpom��
���ĸ��̳� ģ��Maven Model��
	�����ʽΪjar��
		dao��data access object��ʹ�������򹤳̣�
		pojo��Plain Old Java Object����JavaBeans��
		interface�����ڣ�
	�����ʽΪwar��
		service���ѷ����ۺ�Ϊһ�飩
		service��������dao
		dao����pojo
	����ʱֱ�����оۺϹ���manager
	
mybatis��spring�����Ϸ���manager

mabatis�����ļ���
1. ����daoҲ�У�����dao������jar����
���������ļ�Ҳ������jar���������������ļ����ʸ�jar������������ļ���Ƚ��鷳
һ������������еĹ�������service��war������
2. ����ڣ�
/taotao-manager/taotao-manager-service/src/main/resources/mybatis/SqlMapConfig.xml

------------
manager-pojo��com.taotao.pojo�µ�bean
Ϊ���е�bean���Serializable���л��ӿ�
------------
Error��
�������û�гɹ�����manager-service��resources�������log4j.properties�ļ�