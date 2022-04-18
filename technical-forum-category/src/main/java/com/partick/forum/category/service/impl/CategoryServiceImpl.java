package com.partick.forum.category.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.partick.forum.category.service.CategoryService;
import com.partick.forum.common.db.mapper.TCategoryMapper;
import com.partick.forum.common.db.pojo.TCategory;
import com.partick.forum.common.utils.DataBaseDefaultFieldUtil;
import com.partick.forum.common.vo.CategoriesVO;
import com.partick.forum.common.vo.CategoryVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author partick_peng
 */
@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    @Resource
    private TCategoryMapper categoryMapper;

    /**
     * 创建分类信息
     *
     * @param categoriesVOS
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createCategory(CategoriesVO categoriesVOS) {
        List<CategoryVO> categoriesVO =
                categoriesVOS.getCategoriesVO();
        String forumId = categoriesVOS.getForumId();
        for (CategoryVO categoryVO : categoriesVO) {
            if (categoryMapper.selectCount(new QueryWrapper<TCategory>()
                    .eq("category_name", categoryVO.getCategoryName())
                    .eq("forum_id", forumId)
                    .eq("is_deleted", 0)
                    ) >=1) {
                log.error("单据名字重复，分类名称为" + categoryVO.getCategoryName());
                continue;
            }

            TCategory category = new TCategory();
            BeanUtils.copyProperties(categoryVO, category);
            categoriesVO = null;
            category.setForumId(forumId);
            try {
                DataBaseDefaultFieldUtil.defaultCreateField(category);
            } catch (Exception e) {
                e.printStackTrace();
            }
            category.setCategoryId(IdUtil.simpleUUID());
            categoryMapper.insert(category);
            category = null;
        }
    }

    /**
     * 删除分类信息
     *
     * @param categoriesVOS
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delCategory(CategoriesVO categoriesVOS) {
        List<CategoryVO> categoriesVO =
                categoriesVOS.getCategoriesVO();
        for (CategoryVO categoryVO : categoriesVO) {
            TCategory category = new TCategory();
            BeanUtils.copyProperties(categoryVO, category);
            categoriesVO = null;
            try {
                DataBaseDefaultFieldUtil.defaultUpdateField(category);
            } catch (Exception e) {
                e.printStackTrace();
            }
            category.setIsDeleted(1);
            categoryMapper.update(category, new UpdateWrapper<TCategory>()
                    .eq("category_id", category.getCategoryId()));
            category = null;
        }
    }

    /**
     * 通过id获取分类信息
     *
     * @param forumId
     * @return
     */
    @Override
    public CategoriesVO getCategoryById(String forumId) {
        List<CategoryVO> categoryVOList = categoryMapper.queryByForumId(forumId);
        CategoriesVO categoriesVO = new CategoriesVO();
        categoriesVO.setForumId(forumId);
        categoriesVO.setCategoriesVO(categoryVOList);
        return categoriesVO;
    }

    /**
     * 获取中间分类路径
     *
     * @param forumName
     * @param categoryName
     * @return
     */
    @Override
    public String getMiddleCategoryUrl(String forumName,String categoryName) {
        return categoryMapper.queryMiddleUrlByForumNameAndCategoryName(forumName,categoryName);
    }

    /**
     * 通过论坛名字和分类名字获取分类id
     *
     * @param forumName
     * @param categoryName
     * @return
     */
    @Override
    public String getCategoryId(String forumName, String categoryName) {
        return categoryMapper.queryCategoryIdByForumNameAndCategoryName(forumName, categoryName);
    }

}
