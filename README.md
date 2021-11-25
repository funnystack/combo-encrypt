# combo-encrypt
基于mybatis的数据库字段加解密工具


在现代企业的应用中，姓名和手机号等用户的关键信息在数据库中都是需要加密存储的，
所以本框架提倡的方法是规定某一个加密字段在数据库在数据库对应 `_encrypt` 字段以及`_hash`字段。其中：

`_encrypt`字段是根据秘钥加密后的字段,不同的系统的的秘钥不一样，加密出来的值也是不一样的。

`_hash`字段为md5运算之后的字段，主要为了在数据里统计做上下文连接使用。

例: user_name 真正存储的列是`user_name_encrypt` 和  `user_name_hash`

##### 加解密模式：

`local` : 本机加解密，加密算法默认使用DES，Hash算法默认使用MD5。

`rpc`  ：  调用外部接口获取获取加密后和hash后的值。链接的地址为combo-encrypt-web的地址

##### 使用配置：

- Spring方式

```
<bean class="com.funny.combo.encrypt.service.EncryptHolder">
	<property name="appId" value="trade-user"/>
	<property name="model" value="local"/>
</bean>
```

- Springboot starter方式

```
combo:
  encrypt:
    model: local
    appId: trade-user
```

##### 使用方法：

- Mybatis配置方式：查询语句在mybatis的xml里resultMap里配置handler

```
<result column="cons_name_encrypt" jdbcType="VARCHAR" property="consName" typeHandler="com.funny.combo.encrypt.handler.CryptTypeHandler"/>
```

insert or update 语句在mybatis的sql里配置handler

```
<if test="consEmail != null">
    #{consEmail,jdbcType=VARCHAR,typeHandler=com.funny.combo.encrypt.handler.CryptTypeHandle},
</if>

<if test="consEmail != null">
    #{consEmail,jdbcType=VARCHAR,typeHandler=com.funny.combo.encrypt.handler.HashTypeHandler},
</if>
```

- 使用工具类直接加密，然后放到对应实体字段上

```

Data data = new Data();
EncryptEntity encrypt = EncryptUtils.encryption("18610508181");
data.setUserPhoneEncrypt(encrypt.getEncrypted());
data.setuserPhoneHash(encrypt.getHash());

data.insert();
```

- 使用工具类批量加密

```

Map<String, EncryptEntity> encryptMap = EncryptUtils.encryption(Lists.newArrayList("aaa","bbb"));
```
