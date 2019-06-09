package com.andy;

import com.andy.model.People;
import com.andy.model.mapper.PeopleRepository;
import com.sun.org.apache.bcel.internal.classfile.SourceFile;
import org.apache.lucene.util.QueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.metrics.avg.InternalAvg;
import org.elasticsearch.search.sort.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EsApplicationTest {
    private String[] name = {"凌寒", "涵易", "安寒", "紫夏", "凌旋", "听南", "念蕾", "紫夏", "芷梦", "惜玉", "听南"};
    private String[] context = {
            "一夜之间，一场雷电引发的山火烧毁了美丽的“森林庄园”，刚刚从祖父那里继承了这座庄园的保罗·迪克陷入了一筹莫展的境地。",
            "他经受不起打击，闭门不出，茶饭不思，眼睛熬出了血丝。一个多月过去了，年已古稀的外祖母获悉此事，意味深长地对保罗说：“小伙子，庄园成了废墟并不可怕，可怕的是你的眼睛失去了光泽，一天一天地老去。一双老去的眼睛，怎么能看得见希望？”",
            "保罗在外祖母的劝说下，一个人走出了庄园。",
            "他漫无目的地闲逛，在一条街道的拐弯处，他看到一家店铺的门前人头攒动，原来是一些家庭主妇正在排队购买木炭。那一块块躺在纸箱里的木炭忽然让保罗的眼睛一亮，使他看到了一线希望。于是在接下来的两个星期里，保罗雇了几名烧炭工，将庄园里烧焦的树木加工成优质的木炭，送到集市上的木炭经销店",
            "结果，木炭被抢购一空，他因此得到一笔不菲的收入。然后他用这笔收入购买了一大批新树苗，于是一个新的庄园初具规模了。几年以后，“森林庄园”再度绿意盎然。",
            "请记住：别让眼睛老去，才不会让心灵荒芜。",
            "眼睛如果老去，就无法看到希望，没有希望的人生，终将失去存在的意义。积极乐观地生活，忘掉悲伤与不幸，你一定会拥有无限的快乐。当你沉湎于曾经的悲痛时，也将失去今日的欢愉。挫折和痛苦来临之时，也许正是通往成功的开始."
    };

    @Autowired
    private ElasticsearchTemplate template;

    @Autowired
    private PeopleRepository repository;

    /**
     * 创建索引和映射
     */
    @Test
    public void createIndexMapping() {
        template.createIndex(People.class);
        template.putMapping(People.class);
    }

    /**
     * 获取映射
     */
    @Test
    public void getMapping() {
        template.getMapping(People.class).forEach((k, v) ->
                System.out.println(v)
        );
    }

    /**
     * 保存数据
     */
    @Test
    public void saveData() {
        for (long i = 6; i < 100; i++) {
            repository.save(
                    new People(i,
                            name[new Random().nextInt(name.length)],
                            context[new Random().nextInt(context.length)]));
        }
    }

    /**
     * 批量保存数据
     */
    @Test
    public void saveAllData() {
        ArrayList<People> people = new ArrayList<People>() {{
            add(new People(1l, "Number", "好的呢...."));
            add(new People(2l, "Long", "不行...."));
            add(new People(3l, "Integer", "唉~~~~"));
            add(new People(4l, "String", "OK!!!!///////"));
        }};
        Iterable iterable = repository.saveAll(people);
        iterable.forEach(System.out::println);
    }

    /**
     * 查询所有数据
     */
    @Test
    public void findData() {
        repository.findAll().forEach(System.out::println);
    }

    /**
     * 修改
     */
    @Test
    public void updateData() {
        People people = new People() {{
            setId(1l);
            setLastName("NaNa");
            setContext("“前程无忧”(NASDAQ:JOBS)是中国具有广泛影响力的人力资源服务供应商,在美国上市的中国人力资源服务企业,创立了网站+猎头+RPO+校园招聘+管理软件的全方位招聘方案.目前51Job有效简历数量超过1.2亿,日招聘职位过500万,每周多达5,000万份简历通过51Job发送到企业.");
        }};
        repository.save(people);
    }

    /**
     * 删除
     */
    @Test
    public void deleteData() {
        People people = new People() {{
            setId(1l);
        }};
        repository.delete(people);
    }

    /**
     * 分页查询数据
     */
    @Test
    public void findPageData() {
        repository.findAll(PageRequest.of(0, 20, new Sort(Sort.Direction.DESC, "id"))).forEach(System.out::println);
    }

    /**
     *
     */
    @Test
    public void test6() {
        repository.getAllByIdBetweenOrLastNameEquals(
                1l, 20l, "寒", PageRequest.of(0, 20))
                .forEach(System.out::println);
    }

    @Test
    public void test7() {
        repository.getAllByIdEqualsOrLastNameEquals(36l, "凌寒").forEach(System.out::println);
    }

    /**
     * 以下是复杂查询
     */
    @Test
    public void test8() {
        MatchQueryBuilder query = QueryBuilders.matchQuery("lastName", "听南");
        repository.search(query).forEach(System.out::println);
    }


    /**
     * 自定义查询
     */

    @Test
    public void test9() {//term ===>词条查询
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        builder.withQuery(QueryBuilders.termQuery("lastName", "凌寒"));
        Page<People> page = repository.search(builder.build());
        page.forEach(System.out::println);
        System.out.println(page.getTotalElements());
        System.out.println(page.getTotalPages());
    }

    @Test
    public void test10() {//match===>匹配查询
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        builder.withQuery(QueryBuilders.matchQuery("context", "庄园"));
        Page<People> page = repository.search(builder.build());
        page.forEach(System.out::println);
        System.out.println(page.getTotalElements());
    }

    @Test
    public void test11() {//bool===>布尔查询
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        builder.withQuery(QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("context", "庄园")));
        Page<People> page = repository.search(builder.build());
        page.forEach(System.out::println);
        System.out.println(page.getTotalElements());
    }

    @Test
    public void test12() {//fuzzy===>容错查询（最多错两个）
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        builder.withQuery(QueryBuilders.fuzzyQuery("lastName", "凌"));
        Page<People> page = repository.search(builder.build());
        page.forEach(System.out::println);
        System.out.println(page.getTotalElements());
    }

    @Test
    public void test13() {// ? 表示询问一个未知的占位符，* 表示询问0到n个任意占位符 模糊匹配
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        builder.withQuery(QueryBuilders.wildcardQuery("lastName", "*寒"));
        Page<People> page = repository.search(builder.build());
        page.forEach(System.out::println);
        System.out.println(page.getTotalElements());
    }

    @Test
    public void test14() { //分页查询 PageRequest
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        builder.withQuery(QueryBuilders.matchAllQuery());
        builder.withPageable(PageRequest.of(0, 10, Sort.Direction.ASC, "id"));
        Page<People> page = repository.search(builder.build());
        page.forEach(System.out::println);
        System.out.println(page.getTotalElements());
    }

    @Test
    public void test15() {//排序查询（使查询结果按指定字段排序，基于模糊查询）
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        builder.withQuery(QueryBuilders.wildcardQuery("lastName", "*寒"));
        builder.withPageable(PageRequest.of(0, 10));
        builder.withSort(SortBuilders.fieldSort("id").order(SortOrder.ASC));
        Page<People> page = repository.search(builder.build());
        page.forEach(System.out::println);
        System.out.println(page.getTotalElements());
    }

    @Test
    public void test16() {//组合模糊查询，分页，排序
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        builder.withQuery(QueryBuilders.wildcardQuery("lastName", "*寒"));
        builder.withPageable(PageRequest.of(0, 10));
        builder.withSort(SortBuilders.fieldSort("id").order(SortOrder.ASC));
        Page<People> page = repository.search(builder.build());
        page.forEach(System.out::println);
        System.out.println(page.getTotalElements());
    }

    @Test
    public void test17() {
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        //不查询结果
        builder.withSourceFilter(new FetchSourceFilter(new String[]{""}, null));

        //添加聚合
        builder.addAggregation(AggregationBuilders.terms("lastNames").field("lastName"));

        //查询,需要把结果强转为AggregatedPage类型
        AggregatedPage<People> aPage = (AggregatedPage<People>) repository.search(builder.build());

        //解析
        //从结果中取出名为lastNames的那个聚合，
        // 因为是利用String类型字段来进行的term聚合，所以结果要强转为StringTerms类型(LongTerms...)
        StringTerms lastNames = (StringTerms) aPage.getAggregation("lastNames");

        //获取桶
        List<StringTerms.Bucket> list = lastNames.getBuckets();
        list.forEach(e -> {
            System.out.println("e.getKey() = " + e.getKey());
            System.out.println("e.getKeyAsString() = " + e.getKeyAsString());
            System.out.println("e.getDocCount() = " + e.getDocCount());
            System.err.println("------------------");
        });
    }

    @Test
    public void test18() {//嵌套聚合，求平均值
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        //不查询结果
        builder.withSourceFilter(new FetchSourceFilter(new String[]{""}, null));
        //添加聚合
        builder.addAggregation(AggregationBuilders
                .terms("lastNames")
                .field("lastName")
                /*.subAggregation(AggregationBuilders.avg("ids").field("id"))*/);

        //将查询结果强制转换为
        AggregatedPage<People> aPage = (AggregatedPage<People>) repository.search(builder.build());

        StringTerms lastNames = (StringTerms) aPage.getAggregation("lastNames");

        List<StringTerms.Bucket> list = lastNames.getBuckets();

        list.forEach(e -> {
            InternalAvg avg = (InternalAvg) e.getAggregations().asMap().get("ids");
            double value = avg.getValue();
        });

    }
}
