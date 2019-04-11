package com.bigdata.olearn.util;

import java.util.ArrayList;
import java.util.List;

import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.DicAnalysis;
import org.ansj.splitWord.analysis.ToAnalysis;


public class GetKeyWords
{

    public GetKeyWords()
    {
        System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
    }

    public List<String>  getWords(String content)
    {
//        Result termList = ToAnalysis.parse(content);
        Result termList = DicAnalysis.parse(content);

//        System.out.println(termList);
        List<String> wordList = new ArrayList<String>();
        for (Term t : termList)
        {
            if (shouldInclude(t))
            {
                wordList.add(t.getName());
            }
        }
//        String result = "[";
//        for (int i = 0; i < wordList.size(); ++i)
//        {
//            result += wordList.get(i) + ',';
//        }
//        result += "]";
//        System.out.println("=============="+wordList.toString());
        return wordList;
    }
//    public static void main(String[] args) throws Exception
//    {
//        String content = " 任职要求："
//        		+ "1、至少2年以上相关工作经验，计算机或IT相关专业，大专或大专以上学历； "
//        		+ "2、熟悉PC机硬件维护，各种网络设备基本维护；对服务器、路由器、防火墙能够熟练操作及维护，具备故障诊断和处理能力;"
//        		+ "3、熟悉Windows系统安装和维护，熟悉域操作；以及 Windows平台下的各种应用系统的使用、管理和维护工作；"
//        		+ "4、熟悉网络营销渠道，拥有较丰富的网络推广经验和互联网资源;"
//        		+ "5、有较强的文字功底，具备网站专题策划和信息采编能力；"
//        		+ "6、有良好的职业素养、敬业精神及团队精神，擅于沟通;"
//        		+ "7、知晓网站的运作和互联网推广的多种方式，拥有成功的推广经验者可获优先考虑"
//        		+ "8、有一定的数据分析知识和能力，熟悉各大搜索引擎尤其是百度排名算法及流程；"
//        		+ "9、对网络营销有兴趣,具备对产品、项目包装策划能力，一定的文案功底；"
//        		+ "10、踏实、好学钻研、诚实守信、有责任感、有沟通能力、有团队精神。工作地址北京经济技术开发区荣华南路2号院大族广场T5-1501";
//
//        System.out.println(new GetKeyWords().getKeyWords(content));
//    }
    /**
     * 根据穿回来的字符串提取能力关键词
     * @param str         一大段字符串

     * @return
     */
    public List<String> getKeyWords(String str)
    {
        try
        {
            List<String> words = new ArrayList<String>();

            words = new GetKeyWords().getWords(str);

            return words;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 是否应当将这个term纳入计算，词性属于名词、动词、副词、形容词
     * @param term
     * @return 是否应当
     */
    public boolean shouldInclude(Term term)
    {
/******************提出制定词性的词语**************************/
        if (term.getNatureStr().equals("userdefine"))
        {
            return true;
        }
        return false;
    }
    public static void main(String[] args) throws Exception
    {
        System.out.println(new GetKeyWords()
                .getWords("工作职责:1、负责公司各类数据的处理、大数据平台框架的研发设计工作；2、使用Spark、MapReduce、Storm、Kafka等组件进行数据处理；3、新技术框架和解决方案预研与落地，以提高处理和分析大数据的效率和速度。任职资格:1、熟悉Hadoop以及Hadoop生态圈中的多个组件，如HBase、Hive、Kafka、Storm、Impala等；2、精通JAVA编程语言，熟悉Linux操作，可以编写代码编程使用Hadoop生态中的组件和基于组件开发的大数据处理；3、熟悉开源组件源码者优先。"));
//		System.out.println(new GetKeyWords().getWords("Hadoop"));
    }
}

