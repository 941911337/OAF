package cux.oracle.apps.ap.oie.util;
import java.util.StringTokenizer;
public class CSVTool {
          private String newLineSign = "NewLineSign";
          private String enclosedByField = "\"";
          private char  enclosedByChar = '"';
          private int m_numberSampleData = 50000;
          private boolean autoHeaderFlag = false;
          public String getHeaderLine(String content)
          {
            StringTokenizer stringtokenizer = new StringTokenizer(content, "\n", true);
            return findNextRow(stringtokenizer);
          }
          protected int numberOfElclosedByChars(String s)
          {
            enclosedByChar = enclosedByField.charAt(0);
            int i = 0;
            int j = s.length();
            for(int k = 0; k < j; k++)
              if(s.charAt(k) == enclosedByChar) i++;
            return i;
          }
         
          protected String findNextRow(StringTokenizer stringtokenizer)
          {
            StringBuffer stringbuffer = new StringBuffer("");
            Object obj = null;
            int i = 0;
            String s = "";
            for(boolean flag = true; stringtokenizer.hasMoreTokens() && flag; stringbuffer.append(s))
            {
              s = stringtokenizer.nextToken();
              i += numberOfElclosedByChars(s);
              flag = i % 2 != 0;
            }
            if(stringbuffer.toString().length() > 0)
              return stringbuffer.toString();
            else
              return null;
          }
          public String[] getSampleData(String content)
          {
            StringTokenizer stringtokenizer = new StringTokenizer(content, "\n", false);
            int i = 0;
            m_numberSampleData = m_numberSampleData < 0 ? 3 : m_numberSampleData;
            String as[] = new String[m_numberSampleData];
            String s = null;
            if(!autoHeaderFlag)
              findNextRow(stringtokenizer);
            while((s = findNextRow(stringtokenizer)) != null && i < m_numberSampleData) 
              as[i++] = s;
            return as;
         } 
}