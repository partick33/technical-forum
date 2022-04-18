package com.partick.forum.category.jsoup.impl;

import com.partick.forum.category.jsoup.ArticleFromJsoup;
import com.partick.forum.common.mongodb.pojo.ArticleDetail;
import com.partick.forum.common.mongodb.pojo.ArticleInfo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author partick_peng
 */
@Component
public class ArticleFromJsoupImpl implements ArticleFromJsoup {

    /**
     * 从开源中国获取文章信息
     *
     * @param url
     * @return
     */
    @Override
    public List<ArticleInfo> getOschinaArticleInfoFromJsoup(String url) {
        ArrayList<ArticleInfo> articleInfos = new ArrayList<>();
        try {
            Document document = Jsoup.connect(url).get();
            Elements elements = document.getElementsByClass("item blog-item");
            document = null;
            for (int i = 0; i < elements.size(); i++) {
                Element element = elements.get(i);
                Elements header = element.select("a");
                Elements summary = element.select("p");
                ArticleInfo articleInfo = new ArticleInfo();
                articleInfo.setTitle(header.attr("title"));
                articleInfo.setUrl(header.attr("href"));
                articleInfo.setSummary(summary.text());
                articleInfos.add(articleInfo);
            }
            elements = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return articleInfos;
    }

    /**
     * 从思否获取文章信息
     *
     * @param url
     * @return
     */
    @Override
    public List<ArticleInfo> getSegmentFaultArticleInfoFromJsoup(String url) {
        ArrayList<ArticleInfo> articleInfos = new ArrayList<>();
        try {
            Document document = Jsoup.connect(url).get();
            Elements elements = document.getElementsByClass("item-wrap py-3 mb-2 mb-sm-0 list-group-item");
            document = null;
            for (int i = 0; i < elements.size(); i++) {
                Element element = elements.get(i);
                Elements header = element.select("a");
                ArticleInfo articleInfo = new ArticleInfo();
                articleInfo.setTitle(header.text());
                articleInfo.setUrl("https://segmentfault.com" + header.attr("href"));
                articleInfos.add(articleInfo);
            }
            elements = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return articleInfos;
    }

    /**
     * 从博客园获取文章信息
     *
     * @param url
     * @return
     */
    @Override
    public List<ArticleInfo> getCnBlogsArticleInfoFromJsoup(String url) {
        ArrayList<ArticleInfo> articleInfos = new ArrayList<>();
        try {
            Document document = Jsoup.connect(url).get();
            Elements elements = document.getElementsByClass("post-item-body");
            document = null;
            for (int i = 0; i < elements.size(); i++) {
                Element element = elements.get(i);
                Element header = element.select("a").first();
                Elements summary = element.select("p");
                ArticleInfo articleInfo = new ArticleInfo();
                articleInfo.setTitle(header.text());
                articleInfo.setUrl(header.attr("href"));
                articleInfo.setSummary(summary.text());
                articleInfos.add(articleInfo);
            }
            elements = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return articleInfos;
    }

    /**
     * 从开源中国获取文章信息
     *
     * @param url
     * @return
     */
    @Override
    public ArticleDetail getOschinaArticleDetailFromJsoup(String url) {
        ArticleDetail articleDetail = new ArticleDetail();
        try {
            Document document = Jsoup.connect(url).get();
            Elements elementsByClass = document.getElementsByClass("detail-box");
            articleDetail.setText(elementsByClass.html());
            elementsByClass = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return articleDetail;
    }

    /**
     * 从思否获取文章信息
     *
     * @param url
     * @return
     */
    @Override
    public ArticleDetail getSegmentFaultArticleDetailFromJsoup(String url) {
        ArticleDetail articleDetail = new ArticleDetail();
        try {
            Document document = Jsoup.connect(url).get();
            Elements elementsByClass = document.getElementsByClass("article fmt article-content ");
            articleDetail.setText(elementsByClass.html());
            elementsByClass = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return articleDetail;
    }

    /**
     * 从博客园获取文章信息
     *
     * @param url
     * @return
     */
    @Override
    public ArticleDetail getCnBlogsArticleDetailFromJsoup(String url) {
        ArticleDetail articleDetail = new ArticleDetail();
        try {
            Document document = Jsoup.connect(url).get();
            Elements elementsByClass = document.getElementsByClass("post");
            articleDetail.setText(elementsByClass.html());
            elementsByClass = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return articleDetail;
    }
}