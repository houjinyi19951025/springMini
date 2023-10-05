package com.mini.batis;

import com.mini.beans.factory.annotation.Autowired;
import com.mini.jdbc.core.JdbcTemplate;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DefaultSqlSessionFactory implements SqlSessionFactory{
    @Autowired
    JdbcTemplate jdbcTemplate;

    String mapperLocations;

    Map<String,MapperNode> mapperNodeMap = new HashMap<>();

    public Map<String, MapperNode> getMapperNodeMap() {
        return mapperNodeMap;
    }

    public DefaultSqlSessionFactory() {

    }

    public void init(){
      scanLocation(this.mapperLocations);
        this.mapperNodeMap.forEach((k,v)->{
            System.out.println(".......k"+k+"............v"+v);
        });

    }

    private  void scanLocation(String location){
        URL resource = this.getClass().getClassLoader().getResource("");
        System.out.println(".................resource"+resource);
        String sLocationPath = resource.getPath() + location;
        System.out.println("...........sLocationPath"+sLocationPath);
        File dir = new File(sLocationPath);
        for(File file :dir.listFiles()){
            if(file.isDirectory()){
                scanLocation(location+"/"+file.getName());
            }else{
                buildMapperNodes(location+"/"+file.getName());
            }
        }

    }

    private  Map<String, MapperNode>  buildMapperNodes(String filePath) {
        System.out.println(".................."+filePath);
        SAXReader saxReader = new SAXReader();
        URL xmlPath = this.getClass().getClassLoader().getResource(filePath);
        try {
            Document document = saxReader.read(xmlPath);
            Element rootElement = document.getRootElement();
            String namespace = rootElement.attributeValue("namespace");
            Iterator<Element> nodes = rootElement.elementIterator();
            while (nodes.hasNext()){
                Element node = nodes.next();
                String id = node.attributeValue("id");
                String parameterType = node.attributeValue("parameterType");
                String resultType = node.attributeValue("resultType");
                String sql = node.getText();
                MapperNode selectNode = new MapperNode();
                selectNode.setNamespace(namespace);
                selectNode.setId(id);
                selectNode.setSql(sql);
                selectNode.setParameterType(parameterType);
                selectNode.setResultType(resultType);
                selectNode.setParameter("");
                this.mapperNodeMap.put(namespace+"."+id,selectNode);
            }
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
        return this.mapperNodeMap;
    }

    @Override
    public SqlSession openSession() {
        init();
        DefaultSqlSession defaultSqlSession = new DefaultSqlSession();
        defaultSqlSession.setJdbcTemplate(jdbcTemplate);
        defaultSqlSession.setSqlSessionFactory(this);
        return defaultSqlSession;
    }

    @Override
    public MapperNode getMapperNode(String name) {
        return this.mapperNodeMap.get(name);
    }

    public String getMapperLocations() {
        return mapperLocations;
    }

    public void setMapperLocations(String mapperLocations) {
        this.mapperLocations = mapperLocations;
    }
}
