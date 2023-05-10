package jiminkkk.practice.cleanarchitecturepracitce.account.adapter.web;

import jiminkkk.practice.cleanarchitecturepracitce.account.application.port.in.CreateAccountUseCase;
import jiminkkk.practice.cleanarchitecturepracitce.account.application.port.in.GetAccountBalanceQuery;
import jiminkkk.practice.cleanarchitecturepracitce.account.application.port.in.SendMoneyUseCase;
import jiminkkk.practice.cleanarchitecturepracitce.account.domain.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// 계좌 리소스와 관련된 모든 것이 AccountController 클래스에 모여있을 경우
// 많은 곳에서 AccountResource를 공유 -> uu
@RestController
@RequiredArgsConstructor
public class AccountController {
    private final GetAccountBalanceQuery getAccountBalanceQuery;
    private final ListAccountQuery listAccountQuery;
    private final LoadAccountQuery loadAccountQuery;

    private final SendMoneyUseCase sendMoneyUseCase;
    private final CreateAccountUseCase createAccountUseCase;

    // 계좌 목록 반환
    @GetMapping("/accounts")
    List<AccountResource> listAccounts() {

    }

    // 계좌 반환
    @GetMapping("/accounts/{accountId}")
    AccountResource getAccount(@PathVariable("accountId")Long accountId) {

    }

    // 잔액 조회
    @GetMapping("/accounts/{accountId}/balance")
    long getAccountBalance(@PathVariable("accountId") Long accountId) {

    }

    // 새 계좌 생성
    @PostMapping("/accounts")
    AccountResource createAccount(@RequestBody AccountResource account) {

    }

    // source -> target 으로 송금
    @PostMapping("/account/send/{sourceAccountId}/{targetAccountId}/{amount}")
    void sendMoney(@PathVariable("sourceAccountId") Long sourceAccountId,
                   @PathVariable("targetAccountId") Long targetAccountId,
                   @PathVariable("amount") Long amount) {

    }
}
