package com.partick.forum.category.jsoup.impl;

import com.partick.forum.category.jsoup.ForumCategoryFromJsoup;
import com.partick.forum.category.service.CategoryService;
import com.partick.forum.common.vo.ForumCategoryVO;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author partick_peng
 */
@Component
public class ForumCategoryFromJsoupImpl implements ForumCategoryFromJsoup {

    @Resource
    private CategoryService categoryService;

    @Override
    public List<ForumCategoryVO> getOschinaForumCategoryFromJsoup(String categoryName) {
        ArrayList<ForumCategoryVO> forumCategoryVOS = new ArrayList<>();
        try {
            String categoryUrl = categoryService.getMiddleCategoryUrl("开源中国",categoryName);
            Document document = Jsoup.connect(categoryUrl).get();
            Elements elements = document.getElementsByClass("blog-item item");
            document = null;
            for (int i = 0; i < elements.size(); i++) {
                Element element = elements.get(i);
                String url = element.attr("href");
                //开源中国的html中的分类地址包含了中间地址
                url = url.substring(categoryUrl.getBytes(StandardCharsets.UTF_8).length);
                ForumCategoryVO forumCategoryVO = new ForumCategoryVO();
                forumCategoryVO.setForumNavUrl(url);
                forumCategoryVO.setForumNav(element.text().substring(1).trim());
                forumCategoryVOS.add(forumCategoryVO);
            }
            elements = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return forumCategoryVOS;
    }

    /**
     * 从思否获取分类信息
     *
     * @param categoryName
     * @return
     */
    @Override
    public List<ForumCategoryVO> getSegmentFaultForumCategoryFromJsoup(String categoryName) {
        ArrayList<ForumCategoryVO> forumCategoryVOS = new ArrayList<>();
        try {
            String categoryUrl = categoryService.getMiddleCategoryUrl("思否",categoryName);
            Document document = Jsoup.connect(categoryUrl).get();
            Elements elements = document.getElementById("leftNav").getElementsByClass("nav-link");
            document = null;
            for (int i = 0; i < elements.size(); i++) {
                Element element = elements.get(i);
                String url = element.attr("href");
                ForumCategoryVO forumCategoryVO = new ForumCategoryVO();
                forumCategoryVO.setForumNavUrl(url);
                forumCategoryVO.setForumNav(element.text().trim());
                forumCategoryVOS.add(forumCategoryVO);
            }
            elements = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return forumCategoryVOS;
    }

    /**
     * 从博客园获取分类信息
     *
     * @param fileAddr
     * @return
     */
    @Override
    public List<ForumCategoryVO> getCnBlogsForumCategoryFromJsoup(String fileAddr) {
        ArrayList<ForumCategoryVO> forumCategoryVOS = new ArrayList<>();
        try {
            Document document = Jsoup.parse(new File(fileAddr), "utf-8");
            Elements elements = document.select("a");
            for (int i = 0; i < elements.size(); i++) {
                Element element = elements.get(i);
                String url = element.attr("href");
                ForumCategoryVO forumCategoryVO = new ForumCategoryVO();
                forumCategoryVO.setForumNavUrl(url);
                forumCategoryVO.setForumNav(element.text().trim());
                forumCategoryVOS.add(forumCategoryVO);
                System.out.println(forumCategoryVO);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return forumCategoryVOS;
    }
}
