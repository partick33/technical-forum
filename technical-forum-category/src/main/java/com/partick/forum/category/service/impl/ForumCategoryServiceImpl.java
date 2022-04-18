package com.partick.forum.category.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.partick.forum.category.jsoup.ForumCategoryFromJsoup;
import com.partick.forum.category.service.ForumCategoryService;
import com.partick.forum.common.db.mapper.TCategoryMapper;
import com.partick.forum.common.db.mapper.TEleForumCategoryMapper;
import com.partick.forum.common.db.mapper.TEleForumMapper;
import com.partick.forum.common.db.pojo.TCategory;
import com.partick.forum.common.db.pojo.TEleForum;
import com.partick.forum.common.db.pojo.TEleForumCategory;
import com.partick.forum.common.utils.DataBaseDefaultFieldUtil;
import com.partick.forum.common.vo.ForumCategoriesVO;
import com.partick.forum.common.vo.ForumCategoryVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author partick_peng
 */
@Service
@Slf4j
public class ForumCategoryServiceImpl implements ForumCategoryService {

    @Resource
    private TEleForumCategoryMapper eleForumCategoryMapper;

    @Resource
    private TCategoryMapper categoryMapper;

    @Resource
    private ForumCategoryFromJsoup forumCategoryFromJsoup;

    @Resource
    private TEleForumMapper eleForumMapper;

    @Value(value = "${technical-forum.categoryname.segmentfault}")
    private String segmentfault;

    @Value(value = "${technical-forum.categorylocaladdr.cnblogsaddr}")
    private String cnblogsaddr;


    /**
     * 创建论坛分类信息
     *
     * @param forumCategoriesVO
     */
    @Transactional(rollbackFor = Exception.class)
    @Override

    public void createForumCategory(ForumCategoriesVO forumCategoriesVO) {
        List<ForumCategoryVO> categoriesVO =
                forumCategoriesVO.getForumCategoriesVO();
        String categoryId = forumCategoriesVO.getCategoryId();

        TCategory category = categoryMapper
                .selectOne(new QueryWrapper<TCategory>().eq("category_id", categoryId));
        TEleForum eleForum = eleForumMapper.selectOne(new QueryWrapper<TEleForum>().eq("forum_id", category.getForumId()));
        category = null;
        StringBuilder builder = new StringBuilder();
        if (segmentfault.equals(eleForum.getForumName())) {
            builder.append(eleForum.getForumUrl());
        }else {
            String url = categoryMapper.queryMiddleUrlById(categoryId);
            builder.append(url);
        }
        String finalUrl = builder.toString();

        for (ForumCategoryVO categoryVO : categoriesVO) {
            if (eleForumCategoryMapper.selectCount(new QueryWrapper<TEleForumCategory>()
                    .eq("forum_nav", categoryVO.getForumNav())
                    .eq("category_id", categoryId)
                    .eq("is_deleted", 0)
                    ) >= 1) {
                log.error("单据名字重复，分类名称为" + categoryVO.getForumNav());
                continue;
            }

            TEleForumCategory eleForumCategory = new TEleForumCategory();
            BeanUtils.copyProperties(categoryVO, eleForumCategory);
            categoriesVO = null;
            eleForumCategory.setCategoryId(categoryId);
            try {
                DataBaseDefaultFieldUtil.defaultCreateField(eleForumCategory);
            } catch (Exception e) {
                e.printStackTrace();
            }
            eleForumCategory.setForumNavId(IdUtil.simpleUUID());
            eleForumCategory.setFinalUrl(finalUrl.concat(eleForumCategory.getForumNavUrl()));
            eleForumCategoryMapper.insert(eleForumCategory);
            eleForumCategory = null;
        }
    }

    /**
     * 删除论坛分类信息
     *
     * @param forumCategoriesVO
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delForumCategory(ForumCategoriesVO forumCategoriesVO) {
        List<ForumCategoryVO> categoriesVO =
                forumCategoriesVO.getForumCategoriesVO();
        for (ForumCategoryVO categoryVO : categoriesVO) {
            TEleForumCategory eleForumCategory = new TEleForumCategory();
            BeanUtils.copyProperties(categoryVO, eleForumCategory);
            categoriesVO = null;
            try {
                DataBaseDefaultFieldUtil.defaultUpdateField(eleForumCategory);
            } catch (Exception e) {
                e.printStackTrace();
            }
            eleForumCategory.setIsDeleted(1);
            eleForumCategoryMapper.update(eleForumCategory, new UpdateWrapper<TEleForumCategory>()
                    .eq("forum_nav_id", eleForumCategory.getForumNavId()));
            eleForumCategory = null;
        }
    }

    /**
     * 通过id获取论坛分类信息
     *
     * @param categoryId
     * @return
     */
    @Override
    public ForumCategoriesVO getForumCategoryById(String categoryId) {
        List<ForumCategoryVO> categoryVOList = eleForumCategoryMapper.queryByCategoryId(categoryId);
        ForumCategoriesVO forumCategoriesVO = new ForumCategoriesVO();
        forumCategoriesVO.setCategoryId(categoryId);
        forumCategoriesVO.setForumCategoriesVO(categoryVOList);
        return forumCategoriesVO;
    }

    /**
     * 通过论坛id获取论坛分类信息
     *
     * @param forumId
     * @return
     */
    @Override
    public List<ForumCategoryVO> getForumCategoryByForumId(String forumId) {
        return eleForumCategoryMapper.queryByForumId(forumId);
    }

    /**
     * 爬取分类信息
     *
     * @return
     */
    @Override
    public void putForumCategoryInfo() {

        List<ForumCategoryVO> oschinaForumCategoryFromJsoup = forumCategoryFromJsoup
                .getOschinaForumCategoryFromJsoup("博客");
        putForumCategoryInfo(oschinaForumCategoryFromJsoup,"博客");

        List<ForumCategoryVO> segmentFaultForumCategoryFromJsoup = forumCategoryFromJsoup
                .getSegmentFaultForumCategoryFromJsoup("专栏");
        putForumCategoryInfo(segmentFaultForumCategoryFromJsoup, "专栏");

        List<ForumCategoryVO> cnBlogsForumCategoryFromJsoup = forumCategoryFromJsoup
                .getCnBlogsForumCategoryFromJsoup(cnblogsaddr);
        putForumCategoryInfo(cnBlogsForumCategoryFromJsoup, "分类");

    }

    private void putForumCategoryInfo(List<ForumCategoryVO> forumCategoryVOList,String categoryName) {
        TCategory category = categoryMapper.selectOne(new QueryWrapper<TCategory>().eq("category_name", categoryName));
        ForumCategoriesVO forumCategoriesVO = new ForumCategoriesVO();
        forumCategoriesVO.setCategoryId(category.getCategoryId());
        forumCategoriesVO.setForumCategoriesVO(forumCategoryVOList);
        this.createForumCategory(forumCategoriesVO);
    }
}
