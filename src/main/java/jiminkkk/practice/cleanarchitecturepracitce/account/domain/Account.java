package jiminkkk.practice.cleanarchitecturepracitce.account.domain;

// 한 계좌에서 다른 계좌로 송금하는 유스케이스
// 계좌 등록하기와 정보 업데이트 하기의 유스케이스
// 입금과 출력을 할 수 있는 Account 엔티티 생성
// 출금 계좌에서 돈을 출금해 입금 계좌로 돈을 입금하는 유스케이스
// 아래 엔티티는 실제 계좌의 현재 스냅샷 제공 -> 계좌에 대한 모든 입금과 출금은 Activity 엔티티에 포착
import lombok.*;

import java.time.LocalDateTime;

// 최대한 불변성을 유지해야 함

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Account {
    private AccountId id; // 계좌 id
    private Money baselineBalance; // 기준 잔액
    @Getter
    private ActivityWindow activityWindow; // 활동창
    // -> 현재 계좌의 전체 계좌 활동 리스트에서 특정 범위의 계좌 활동만 볼 수 있는 창을 의미

    // 분리한 AccountPersistenceAdapter에서 사용하기 위함
    // 유효한 상태의 Account 엔티티만 생성할 수 있는 팩토리 메서드 제공
    public static Account withoutId(
            Money baselineBalance,
            ActivityWindow activityWindow) {
        return new Account(null, baselineBalance, activityWindow);
    }
    public static Account withId(
            AccountId accountId,
            Money baselineBalance,
            ActivityWindow activityWindow){
        return new Account(accountId, baselineBalance, activityWindow);
    }


    // 현재 총 잔고를 계산하는 메서드
    // -> 기준 잔고에 활동창의 모든 활동들의 잔고를 합한 값
    public Money calculateBalance() {
        return Money.add(
                this.baselineBalance,
                this.activityWindow.calculateBalance(this.id));
    }

    // 출금
    // calculateBalance() 덕분에 새로운 활동을 활동창에 추가하는 역할만 하게 됨
    public boolean withdraw(Money money, AccountId targetAccountId) {
        // 비지니스 규칙 검증
        // 출금 계좌는 초과 인출되어선 안됨
        if (!mayWithdraw(money)) return false;

        Activity withdrawal = new Activity(
                this.id,
                this.id,
                targetAccountId,
                LocalDateTime.now(),
                money);

        this.activityWindow.addActivity(withdrawal);
        return true;
    }


    private boolean mayWithdraw(Money money) {
        return Money.add(
                this.calculateBalance(),
                money.negate()).isPositive();
    }

    // 입금
    // calculateBalance() 덕분에 새로운 활동을 활동창에 추가하는 역할만 하게 됨
    public boolean deposit(Money money, AccountId sourceAccountID) {
        Activity deposit = new Activity(
                this.id,
                sourceAccountID,
                this.id,
                LocalDateTime.now(),
                money
        );
        this.activityWindow.addActivity(deposit);
        return true;
    }

    @Value
    public static class AccountId {
        private Long value;
    }
}
