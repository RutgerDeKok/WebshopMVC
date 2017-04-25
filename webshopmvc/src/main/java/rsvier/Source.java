package rsvier;

import java.util.List;

public class Source {
private String sourceName;
private List<String> testList;

public String getSourceName()
{
    return sourceName;
}

public void setSourceName(String name)
{
    this.sourceName = name;
}

public List<String> getTestList()
{
    return testList;
}

public void setTestList(List<String> list)
{
    this.testList = list;
}

}