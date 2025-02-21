package budhioct.dev.runner;

import budhioct.dev.entity.*;
import budhioct.dev.repository.*;
import budhioct.dev.utilities.Ownership;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class MyCommandLineRunner implements CommandLineRunner {

    private final StockRepository stockRepository;
    private final LogStockRepository logStockRepository;
    private final PertaminaRepository pertaminaRepository;
    private final OfficialAgentRepository officialAgentRepository;
    private final SubAgentRepository subAgentRepository;
    private final DistributionRepository distributionRepository;
    private final TransactionRepository transactionRepository;
    private final FolksyRepository folksyRepository;
    private final Faker faker = new Faker(new Locale("in-ID"));
    private final Random random = new Random();

    Stock pertaminaStock = new Stock(Ownership.PERTAMINA, 10000);
    Stock officialAgentStock1 = new Stock(Ownership.OFFICIAL_AGENT, 500);
    Stock officialAgentStock2 = new Stock(Ownership.OFFICIAL_AGENT, 500);
    Stock officialAgentStock3 = new Stock(Ownership.OFFICIAL_AGENT, 500);
    Stock subAgentStock1 = new Stock(Ownership.SUB_AGENT, 20);
    Stock subAgentStock2 = new Stock(Ownership.SUB_AGENT, 20);
    Stock subAgentStock3 = new Stock(Ownership.SUB_AGENT, 20);
    Stock subAgentStock4 = new Stock(Ownership.SUB_AGENT, 20);
    Stock subAgentStock5 = new Stock(Ownership.SUB_AGENT, 20);
    Stock subAgentStock6 = new Stock(Ownership.SUB_AGENT, 20);
    Stock subAgentStock7 = new Stock(Ownership.SUB_AGENT, 20);
    Stock subAgentStock8 = new Stock(Ownership.SUB_AGENT, 20);
    Stock subAgentStock9 = new Stock(Ownership.SUB_AGENT, 20);

    OfficialAgent officialAgent1 = new OfficialAgent("PT. HASANAH MAKMUR GEMILANG", "JL. KAMBOJA NO.5 WAINGAPU", null,  officialAgentStock1);
    OfficialAgent officialAgent2 = new OfficialAgent("PT PUTRA MANDIRI MA ", "JL. CAKALANG KOMPLEKS PPI KEL. KOTA", null,  officialAgentStock2);
    OfficialAgent officialAgent3 = new OfficialAgent("PT. SYAFAAT KARYA BERSAMA ", "DS.SEPULUH KEC. SEPULUH", null,  officialAgentStock3);

    Pertamina pertamina = new Pertamina("PT Badak NGL", "Wisma Nusantara Lantai 9 Jl. MH Thamrin No. 59 Jakarta Pusat 10350 Indonesia", "+6221-31930243 / 31936317", List.of(officialAgent1, officialAgent2, officialAgent3), pertaminaStock);

    SubAgent subAgent1 = new SubAgent("Sub Agent A", "Jl. Mawar No. 123", officialAgent1, subAgentStock1);
    SubAgent subAgent2 = new SubAgent("Sub Agent B", "Jl. Mawar No. 123", officialAgent1, subAgentStock2);
    SubAgent subAgent3 = new SubAgent("Sub Agent C", "Jl. Mawar No. 123", officialAgent1, subAgentStock3);
    SubAgent subAgent4 = new SubAgent("Sub Agent A", "Jl. Mawar No. 123", officialAgent2, subAgentStock4);
    SubAgent subAgent5 = new SubAgent("Sub Agent B", "Jl. Mawar No. 123", officialAgent2, subAgentStock5);
    SubAgent subAgent6 = new SubAgent("Sub Agent C", "Jl. Mawar No. 123", officialAgent2, subAgentStock6);
    SubAgent subAgent7 = new SubAgent("Sub Agent A", "Jl. Mawar No. 123", officialAgent3, subAgentStock7);
    SubAgent subAgent8 = new SubAgent("Sub Agent B", "Jl. Mawar No. 123", officialAgent3, subAgentStock8);
    SubAgent subAgent9 = new SubAgent("Sub Agent C", "Jl. Mawar No. 123", officialAgent3, subAgentStock9);

    List<Folksy> folksies = new ArrayList<>();
    void addFolksy(){
        for (int i = 0; i < 100; i++) {
            Folksy folksy = new Folksy();
            folksy.setName(faker.name().fullName());
            folksies.add(folksy);
        }
    }

    @Override
    public void run(String... args) throws Exception {
        folksyRepository.deleteAll();
        transactionRepository.deleteAll();
        distributionRepository.deleteAll();
        subAgentRepository.deleteAll();
        officialAgentRepository.deleteAll();
        pertaminaRepository.deleteAll();
        logStockRepository.deleteAll();
        stockRepository.deleteAll();

        // Set Pertamina reference in OfficialAgent
        officialAgent1.setPertamina(pertamina);
        officialAgent2.setPertamina(pertamina);
        officialAgent3.setPertamina(pertamina);

        // Save Pertamina and OfficialAgent
        pertaminaRepository.save(pertamina);
        officialAgentRepository.saveAll(List.of(officialAgent1, officialAgent2, officialAgent3));

        // Save SubAgent
        subAgentRepository.saveAll(List.of(subAgent1, subAgent2, subAgent3, subAgent4, subAgent5, subAgent6, subAgent7, subAgent8, subAgent9));

        // save Folksy
        addFolksy();
        folksyRepository.saveAll(folksies);

        System.out.println("data first success injected to db");
    }

}
