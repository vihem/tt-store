package com.taotao.content.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.JsonUtils;
import com.taotao.content.service.ContentService;
import com.taotao.jedis.JedisClient;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.pojo.TbContentExample.Criteria;

@Service
public class ContentServiceImpl implements ContentService {
	@Autowired
	private TbContentMapper contentMapper;

	@Autowired
	private JedisClient jedisClient;
	
	@Value("${INDEX_CONTENT}")
	private String INDEX_CONTENT;
	
	@Override
	public EasyUIDataGridResult getContentList(Long categoryId, int page, int rows) {
		// 设置分页设置
		PageHelper.startPage(page, rows);
		// 执行查询
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);
		List<TbContent> list = contentMapper.selectByExampleWithBLOBs(example);
//		List<TbContent> list = contentMapper.selectByExample(example);
		// 得到查询结果
		PageInfo<TbContent> pageInfo = new PageInfo<>(list);
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setRows(list);
		result.setTotal(pageInfo.getTotal());
		// 返回结果
		return result;
		// 发布服务到dubbo，让web引用
	}

	@Override
	public TaotaoResult addContent(TbContent content) {
		//补全pojo
		content.setCreated(new Date());
		content.setUpdated(new Date());
		//插入到数据库tb_content
		contentMapper.insert(content);
		try {
			// 同步缓存(删除对应的缓存即可)
			jedisClient.hdel(INDEX_CONTENT, content.getCategoryId().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("添加内容成功 " + content);
		return TaotaoResult.ok();//记得发布服务
	}

	@Override
	public TaotaoResult editContent(TbContent content) {
		// 补全pojo
		content.setUpdated(new Date());
		contentMapper.updateByPrimaryKeyWithBLOBs(content);
		try {
			// 同步缓存(删除对应的缓存即可)
			jedisClient.hdel(INDEX_CONTENT, content.getCategoryId().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("修改内容成功 " + content);
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult deleteContent(List<Long> ids, long categoryId) {
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdIn(ids);
		contentMapper.deleteByExample(example);
		try {
			// 同步缓存(删除对应的缓存即可)
			jedisClient.hdel(INDEX_CONTENT, categoryId + "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return TaotaoResult.ok();
	}
	
	@Override
	public List<TbContent> getContentsByCid(long cid) {
		// 查询缓存
		// 添加缓存，但不能影响正常的业务逻辑
		try {
			// 查询缓存
			String json = jedisClient.hget(INDEX_CONTENT, String.valueOf(cid));
			// 如果查询到结果，把json转为List
			if (StringUtils.isNotBlank(json)) {
				return JsonUtils.jsonToList(json, TbContent.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 缓存中没有，查询数据库
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(cid);
		List<TbContent> list = contentMapper.selectByExample(example);
		/*
		 * 新添加内容时，缓存没有添加该内容，所以需要在添加内容时，把结果添加到缓存,同步缓存
		 */
		try {
			// key=INDEX_CONTENT(首页内容，是不会变的),field=cid(会变的),value=JsonUtils.objectToJson(list)
			jedisClient.hset(INDEX_CONTENT, cid+"", JsonUtils.objectToJson(list));
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 返回结果
		return list;
	}

	@Override
	public List<TbContent> getSwiper() {
		// TODO Auto-generated method stub
		return contentMapper.selectByExample(null);
	}

}
