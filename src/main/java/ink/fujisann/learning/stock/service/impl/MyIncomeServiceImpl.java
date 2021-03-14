package ink.fujisann.learning.stock.service.impl;

import ink.fujisann.learning.stock.pojo.CommonDict;
import ink.fujisann.learning.stock.pojo.IncomeSummary;
import ink.fujisann.learning.stock.repository.CommonDictRepository;
import ink.fujisann.learning.stock.repository.IncomeSummaryRepository;
import ink.fujisann.learning.stock.resp.DayIncomeResp;
import ink.fujisann.learning.stock.service.MyIncomeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class MyIncomeServiceImpl implements MyIncomeService {
    private IncomeSummaryRepository incomeSummaryRepository;

    @Autowired
    public void setIncomeSummaryRepository(IncomeSummaryRepository incomeSummaryRepository) {
        this.incomeSummaryRepository = incomeSummaryRepository;
    }

    private CommonDictRepository commonDictRepository;

    @Autowired
    public void setCommonDictRepository(CommonDictRepository commonDictRepository) {
        this.commonDictRepository = commonDictRepository;
    }

    @Override
    public List<DayIncomeResp> myIncome() {
        IncomeSummary incomeSummary = new IncomeSummary();
        incomeSummary.setDay(new Date());
        Example<IncomeSummary> example = Example.of(incomeSummary);
        List<IncomeSummary> summaryList = incomeSummaryRepository.findAll(example);

        CommonDict commonDict = new CommonDict();
        commonDict.setDictKey("incomeType");
        Example<CommonDict> dictExample = Example.of(commonDict);
        List<CommonDict> dictList = commonDictRepository.findAll(dictExample);

        List<DayIncomeResp> resp = new ArrayList<>();
        summaryList.forEach(incomeSummary1 -> {
            DayIncomeResp tmp = new DayIncomeResp();
            resp.add(tmp);
            BeanUtils.copyProperties(incomeSummary1, tmp);
            String incomeType = incomeSummary1.getIncomeType();
            CommonDict dict = dictList.stream().filter(commonDict1 -> commonDict1.getId().equals(incomeType))
                    .findFirst().orElseThrow(RuntimeException::new);
            tmp.setIncomeName(dict.getContentValue());
        });

        return resp;
    }
}
