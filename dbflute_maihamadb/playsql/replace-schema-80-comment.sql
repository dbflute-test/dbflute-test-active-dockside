
-- MEMBER
comment on table MEMBER is '会員: 会員のプロフィールやアカウントなどの基本情報を保持する。
基本的に物理削除はなく、退会したらステータスが退会会員になる。
ライフサイクルやカテゴリの違う会員情報は、one-to-oneなどの関連テーブルにて。';

-- MEMBER_ADDRESS
comment on column MEMBER_ADDRESS.VALID_BEGIN_DATE is '有効開始日: 一つの有効期間の開始を示す日付。
前の有効終了日の次の日の値が格納される。';
