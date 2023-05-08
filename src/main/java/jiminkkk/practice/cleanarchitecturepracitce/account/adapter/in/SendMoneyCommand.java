package jiminkkk.practice.cleanarchitecturepracitce.account.adapter.in;

import com.sun.istack.NotNull;
import jiminkkk.practice.cleanarchitecturepracitce.account.domain.AccountId;
import jiminkkk.practice.cleanarchitecturepracitce.account.domain.Money;
import jiminkkk.practice.cleanarchitecturepracitce.account.shared.SelfValidating;
import lombok.Getter;

// 입력 유효성 처리 -> 유스케이스 클래스의 책임 X
// 어디서 처리해야 할까? -> 입력 모델 -> 생성자 내에서 입력 유효성 검증
@Getter
public class SendMoneyCommand extends SelfValidating<SendMoneyCommand> {
    @NotNull
    private final AccountId sourceAccountId;
    @NotNull
    private final AccountId targetAccountId;
    @NotNull
    private final Money money;

    public SendMoneyCommand(AccountId sourceAccountId, AccountId targetAccountId, Money money) {
        this.sourceAccountId = sourceAccountId;
        this.targetAccountId = targetAccountId;
        this.money = money;
        requireGreaterThan(money, 0); // 송금액이 0보다 큰지 검사하는 내용
        this.validateSelf(); // 유효성을 검증하는 부분
    }
}
