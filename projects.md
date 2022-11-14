h1. Informe CI/CD Stages de los proyectos WOM

Este informe tiene como finalidad obtener los stage de CI/CD ejecutados en cada proyecto
Ejecutado el 22-11-10 

# h3. WomCorp / wom-digital / forks / ecomm-dev1 / bff / wom-portal-recarga-bff [git|https://gitlab.com/womcorp/wom-digital/forks/ecomm-dev1/bff/wom-portal-recarga-bff]
    Last pipeline  [691185132|https://gitlab.com/womcorp/wom-digital/forks/ecomm-dev1/bff/wom-portal-recarga-bff/-/pipelines/691185132] on develop as success
    Stages :
    ||Stage||link||
    | deploy (deploy-dev1) | [3304769223|https://gitlab.com/womcorp/wom-digital/forks/ecomm-dev1/bff/wom-portal-recarga-bff/-/jobs/3304769223] |
    | build (build-container) | [3304612611|https://gitlab.com/womcorp/wom-digital/forks/ecomm-dev1/bff/wom-portal-recarga-bff/-/jobs/3304612611] |
    | tagging (release-tag) | [3304573688|https://gitlab.com/womcorp/wom-digital/forks/ecomm-dev1/bff/wom-portal-recarga-bff/-/jobs/3304573688] |
    | deploy (deploy-secrets-dev1) | [3304573683|https://gitlab.com/womcorp/wom-digital/forks/ecomm-dev1/bff/wom-portal-recarga-bff/-/jobs/3304573683] |
    | version (version) | [3304573678|https://gitlab.com/womcorp/wom-digital/forks/ecomm-dev1/bff/wom-portal-recarga-bff/-/jobs/3304573678] |
# h3. WomCorp / wom-digital / itr / dialogs / Backup Wom Itr Agent Bot Mobile [git|https://gitlab.com/womcorp/wom-digital/itr/dialogs/backup-wom-itr-agent-bot-mobile]
# h3. WomCorp / wom-digital / itr / dialogs / Backup Wom Itr Agent Bot Fiber [git|https://gitlab.com/womcorp/wom-digital/itr/dialogs/backup-wom-itr-agent-bot-fiber]
# h3. WomCorp / wom-digital / masros / WOM-TV [git|https://gitlab.com/womcorp/wom-digital/masros/wom-tv]
    Last pipeline  [691317710|https://gitlab.com/womcorp/wom-digital/masros/wom-tv/-/pipelines/691317710] on desarrollo as success
    Stages :
    ||Stage||link||
    | restart (RESTART_TOMCAT_DESA) | [3305363501|https://gitlab.com/womcorp/wom-digital/masros/wom-tv/-/jobs/3305363501] |
    | scf_deploy (INSTALL_SCF_DESA) | [3305363500|https://gitlab.com/womcorp/wom-digital/masros/wom-tv/-/jobs/3305363500] |
    | prepare (PREPARE_ENV_DESA) | [3305363498|https://gitlab.com/womcorp/wom-digital/masros/wom-tv/-/jobs/3305363498] |
    | build (SET_ENV_DESA) | [3305363497|https://gitlab.com/womcorp/wom-digital/masros/wom-tv/-/jobs/3305363497] |
# h3. WomCorp / wom-digital / forks / miwom-dev1 / miwom / wom-miwom-front [git|https://gitlab.com/womcorp/wom-digital/forks/miwom-dev1/miwom/wom-miwom-front]
    Last pipeline  [691417835|https://gitlab.com/womcorp/wom-digital/forks/miwom-dev1/miwom/wom-miwom-front/-/pipelines/691417835] on feature/PM2ML-40 as success
    Stages :
    ||Stage||link||
    | verify (yamllint) | [3305895631|https://gitlab.com/womcorp/wom-digital/forks/miwom-dev1/miwom/wom-miwom-front/-/jobs/3305895631] |
# h3. WomCorp / wom-digital / portal-recargas / services / wom-portal-recarga-bff [git|https://gitlab.com/womcorp/wom-digital/portal-recargas/services/wom-portal-recarga-bff]
    Last pipeline  [690271419|https://gitlab.com/womcorp/wom-digital/portal-recargas/services/wom-portal-recarga-bff/-/pipelines/690271419] on uat as success
    Stages :
    ||Stage||link||
    | deploy (deploy-uat) | [3299812648|https://gitlab.com/womcorp/wom-digital/portal-recargas/services/wom-portal-recarga-bff/-/jobs/3299812648] |
    | deploy (deploy-secrets-uat) | [3299782410|https://gitlab.com/womcorp/wom-digital/portal-recargas/services/wom-portal-recarga-bff/-/jobs/3299782410] |
    | secure (gitleaks) | [3299782408|https://gitlab.com/womcorp/wom-digital/portal-recargas/services/wom-portal-recarga-bff/-/jobs/3299782408] |
    | verify (yamllint) | [3299782406|https://gitlab.com/womcorp/wom-digital/portal-recargas/services/wom-portal-recarga-bff/-/jobs/3299782406] |
    | version (version) | [3299782405|https://gitlab.com/womcorp/wom-digital/portal-recargas/services/wom-portal-recarga-bff/-/jobs/3299782405] |
# h3. WomCorp / wom-digital / portal-recargas / front / wom-portal-recarga-front [git|https://gitlab.com/womcorp/wom-digital/portal-recargas/front/wom-portal-recarga-front]
    Last pipeline  [691186125|https://gitlab.com/womcorp/wom-digital/portal-recargas/front/wom-portal-recarga-front/-/pipelines/691186125] on develop as success
    Stages :
    ||Stage||link||
    | deploy (deploy-dev) | [3304580145|https://gitlab.com/womcorp/wom-digital/portal-recargas/front/wom-portal-recarga-front/-/jobs/3304580145] |
    | secure (sast) | [3304580140|https://gitlab.com/womcorp/wom-digital/portal-recargas/front/wom-portal-recarga-front/-/jobs/3304580140] |
    | unit-tests (unit-tests) | [3304580133|https://gitlab.com/womcorp/wom-digital/portal-recargas/front/wom-portal-recarga-front/-/jobs/3304580133] |
    | build (build-dev) | [3304580125|https://gitlab.com/womcorp/wom-digital/portal-recargas/front/wom-portal-recarga-front/-/jobs/3304580125] |
# h3. WomCorp / WordOfMouth / artifacts / charts / miwom-chart [git|https://gitlab.com/womcorp/admin/artifacts/charts/miwom-chart]
    Last pipeline  [676436918|https://gitlab.com/womcorp/admin/artifacts/charts/miwom-chart/-/pipelines/676436918] on main as success
    Stages :
    ||Stage||link||
    | build (generate_chart) | [3224122366|https://gitlab.com/womcorp/admin/artifacts/charts/miwom-chart/-/jobs/3224122366] |
# h3. WomCorp / wom-digital / forks / miwom-dev1 / miwom / miwom-tracking-ms [git|https://gitlab.com/womcorp/wom-digital/forks/miwom-dev1/miwom/miwom-tracking-ms]
    Last pipeline  [690011871|https://gitlab.com/womcorp/wom-digital/forks/miwom-dev1/miwom/miwom-tracking-ms/-/pipelines/690011871] on refs/merge-requests/1/head as success
    Stages :
    ||Stage||link||
    | verify (eslint) | [3298133137|https://gitlab.com/womcorp/wom-digital/forks/miwom-dev1/miwom/miwom-tracking-ms/-/jobs/3298133137] |
    | unit-tests (unit-tests) | [3298133124|https://gitlab.com/womcorp/wom-digital/forks/miwom-dev1/miwom/miwom-tracking-ms/-/jobs/3298133124] |
# h3. WomCorp / wom-digital / forks / miwom-dev1 / miwom / miwom-payment-ms [git|https://gitlab.com/womcorp/wom-digital/forks/miwom-dev1/miwom/miwom-payment-ms]
    Last pipeline  [676642226|https://gitlab.com/womcorp/wom-digital/forks/miwom-dev1/miwom/miwom-payment-ms/-/pipelines/676642226] on templatev2 as success
    Stages :
    ||Stage||link||
    | version (version) | [3225326020|https://gitlab.com/womcorp/wom-digital/forks/miwom-dev1/miwom/miwom-payment-ms/-/jobs/3225326020] |
# h3. WomCorp / wom-digital / forks / miwom-dev1 / miwom / miwom-marketplace-ms [git|https://gitlab.com/womcorp/wom-digital/forks/miwom-dev1/miwom/miwom-marketplace-ms]
    Last pipeline  [688003297|https://gitlab.com/womcorp/wom-digital/forks/miwom-dev1/miwom/miwom-marketplace-ms/-/pipelines/688003297] on develop as success
    Stages :
    ||Stage||link||
    | tagging (release-tag) | [3287058246|https://gitlab.com/womcorp/wom-digital/forks/miwom-dev1/miwom/miwom-marketplace-ms/-/jobs/3287058246] |
    | deploy (deploy-dev1) | [3287058245|https://gitlab.com/womcorp/wom-digital/forks/miwom-dev1/miwom/miwom-marketplace-ms/-/jobs/3287058245] |
    | deploy (deploy-secrets-dev1) | [3287058244|https://gitlab.com/womcorp/wom-digital/forks/miwom-dev1/miwom/miwom-marketplace-ms/-/jobs/3287058244] |
    | secure (sast) | [3287058243|https://gitlab.com/womcorp/wom-digital/forks/miwom-dev1/miwom/miwom-marketplace-ms/-/jobs/3287058243] |
    | sonar (sonar) | [3287058242|https://gitlab.com/womcorp/wom-digital/forks/miwom-dev1/miwom/miwom-marketplace-ms/-/jobs/3287058242] |
    | sonar (eslint) | [3287058241|https://gitlab.com/womcorp/wom-digital/forks/miwom-dev1/miwom/miwom-marketplace-ms/-/jobs/3287058241] |
    | unit-tests (unit-tests) | [3287058240|https://gitlab.com/womcorp/wom-digital/forks/miwom-dev1/miwom/miwom-marketplace-ms/-/jobs/3287058240] |
    | build (build-container) | [3287058239|https://gitlab.com/womcorp/wom-digital/forks/miwom-dev1/miwom/miwom-marketplace-ms/-/jobs/3287058239] |
    | version (version) | [3287058238|https://gitlab.com/womcorp/wom-digital/forks/miwom-dev1/miwom/miwom-marketplace-ms/-/jobs/3287058238] |
# h3. WomCorp / wom-digital / forks / miwom-dev1 / miwom / miwom-invoices-ms [git|https://gitlab.com/womcorp/wom-digital/forks/miwom-dev1/miwom/miwom-invoices-ms]
    Last pipeline  [676638716|https://gitlab.com/womcorp/wom-digital/forks/miwom-dev1/miwom/miwom-invoices-ms/-/pipelines/676638716] on templatev2 as success
    Stages :
    ||Stage||link||
    | version (version) | [3225304442|https://gitlab.com/womcorp/wom-digital/forks/miwom-dev1/miwom/miwom-invoices-ms/-/jobs/3225304442] |
# h3. WomCorp / wom-digital / forks / miwom-dev1 / miwom / miwom-history-ms [git|https://gitlab.com/womcorp/wom-digital/forks/miwom-dev1/miwom/miwom-history-ms]
    Last pipeline  [683961251|https://gitlab.com/womcorp/wom-digital/forks/miwom-dev1/miwom/miwom-history-ms/-/pipelines/683961251] on develop as success
    Stages :
    ||Stage||link||
    | tagging (release-tag) | [3265515110|https://gitlab.com/womcorp/wom-digital/forks/miwom-dev1/miwom/miwom-history-ms/-/jobs/3265515110] |
    | deploy (deploy-dev1) | [3265515106|https://gitlab.com/womcorp/wom-digital/forks/miwom-dev1/miwom/miwom-history-ms/-/jobs/3265515106] |
    | deploy (deploy-secrets-dev1) | [3265515101|https://gitlab.com/womcorp/wom-digital/forks/miwom-dev1/miwom/miwom-history-ms/-/jobs/3265515101] |
    | secure (sast) | [3265515095|https://gitlab.com/womcorp/wom-digital/forks/miwom-dev1/miwom/miwom-history-ms/-/jobs/3265515095] |
    | sonar (sonar) | [3265515087|https://gitlab.com/womcorp/wom-digital/forks/miwom-dev1/miwom/miwom-history-ms/-/jobs/3265515087] |
    | sonar (eslint) | [3265515079|https://gitlab.com/womcorp/wom-digital/forks/miwom-dev1/miwom/miwom-history-ms/-/jobs/3265515079] |
    | unit-tests (unit-tests) | [3265515075|https://gitlab.com/womcorp/wom-digital/forks/miwom-dev1/miwom/miwom-history-ms/-/jobs/3265515075] |
    | build (build-container) | [3265515067|https://gitlab.com/womcorp/wom-digital/forks/miwom-dev1/miwom/miwom-history-ms/-/jobs/3265515067] |
    | version (version) | [3265515058|https://gitlab.com/womcorp/wom-digital/forks/miwom-dev1/miwom/miwom-history-ms/-/jobs/3265515058] |
# h3. WomCorp / wom-digital / forks / miwom-dev1 / miwom / miwom-customer-ms [git|https://gitlab.com/womcorp/wom-digital/forks/miwom-dev1/miwom/miwom-customer-ms]
    Last pipeline  [688099524|https://gitlab.com/womcorp/wom-digital/forks/miwom-dev1/miwom/miwom-customer-ms/-/pipelines/688099524] on refs/merge-requests/11/head as success
    Stages :
    ||Stage||link||
    | sonar (eslint) | [3287639441|https://gitlab.com/womcorp/wom-digital/forks/miwom-dev1/miwom/miwom-customer-ms/-/jobs/3287639441] |
    | unit-tests (unit-tests) | [3287639440|https://gitlab.com/womcorp/wom-digital/forks/miwom-dev1/miwom/miwom-customer-ms/-/jobs/3287639440] |
# h3. WomCorp / wom-digital / forks / miwom-dev1 / miwom / miwom-carrier-billing-ms [git|https://gitlab.com/womcorp/wom-digital/forks/miwom-dev1/miwom/miwom-carrier-billing-ms]
    Last pipeline  [676655988|https://gitlab.com/womcorp/wom-digital/forks/miwom-dev1/miwom/miwom-carrier-billing-ms/-/pipelines/676655988] on templatev2 as success
    Stages :
    ||Stage||link||
    | version (version) | [3225410389|https://gitlab.com/womcorp/wom-digital/forks/miwom-dev1/miwom/miwom-carrier-billing-ms/-/jobs/3225410389] |
# h3. WomCorp / wom-digital / forks / miwom-dev1 / miwom / miwom-contentful-ms [git|https://gitlab.com/womcorp/wom-digital/forks/miwom-dev1/miwom/miwom-contentful-ms]
    Last pipeline  [676620192|https://gitlab.com/womcorp/wom-digital/forks/miwom-dev1/miwom/miwom-contentful-ms/-/pipelines/676620192] on templatev2 as success
    Stages :
    ||Stage||link||
    | version (version) | [3225207039|https://gitlab.com/womcorp/wom-digital/forks/miwom-dev1/miwom/miwom-contentful-ms/-/jobs/3225207039] |
# h3. WomCorp / wom-digital / forks / miwom-dev1 / miwom / miwom-auth-ms [git|https://gitlab.com/womcorp/wom-digital/forks/miwom-dev1/miwom/miwom-auth-ms]
    Last pipeline  [678794757|https://gitlab.com/womcorp/wom-digital/forks/miwom-dev1/miwom/miwom-auth-ms/-/pipelines/678794757] on develop as success
    Stages :
    ||Stage||link||
    | tagging (release-tag) | [3237316672|https://gitlab.com/womcorp/wom-digital/forks/miwom-dev1/miwom/miwom-auth-ms/-/jobs/3237316672] |
    | deploy (deploy-dev1) | [3237316671|https://gitlab.com/womcorp/wom-digital/forks/miwom-dev1/miwom/miwom-auth-ms/-/jobs/3237316671] |
    | deploy (deploy-secrets-dev1) | [3237316670|https://gitlab.com/womcorp/wom-digital/forks/miwom-dev1/miwom/miwom-auth-ms/-/jobs/3237316670] |
    | secure (sast) | [3237316669|https://gitlab.com/womcorp/wom-digital/forks/miwom-dev1/miwom/miwom-auth-ms/-/jobs/3237316669] |
    | sonar (sonar) | [3237316668|https://gitlab.com/womcorp/wom-digital/forks/miwom-dev1/miwom/miwom-auth-ms/-/jobs/3237316668] |
    | sonar (eslint) | [3237316667|https://gitlab.com/womcorp/wom-digital/forks/miwom-dev1/miwom/miwom-auth-ms/-/jobs/3237316667] |
    | unit-tests (unit-tests) | [3237316665|https://gitlab.com/womcorp/wom-digital/forks/miwom-dev1/miwom/miwom-auth-ms/-/jobs/3237316665] |
    | build (build-container) | [3237316663|https://gitlab.com/womcorp/wom-digital/forks/miwom-dev1/miwom/miwom-auth-ms/-/jobs/3237316663] |
    | version (version) | [3237316661|https://gitlab.com/womcorp/wom-digital/forks/miwom-dev1/miwom/miwom-auth-ms/-/jobs/3237316661] |
# h3. WomCorp / wom-digital / miwom / charts / miwom-ingress-clone1 [git|https://gitlab.com/womcorp/wom-digital/miwom/charts/miwom-ingress-clone-1]
    Last pipeline  [672699923|https://gitlab.com/womcorp/wom-digital/miwom/charts/miwom-ingress-clone-1/-/pipelines/672699923] on main as success
    Stages :
    ||Stage||link||
    | deploy (deploy-dev) | [3205558476|https://gitlab.com/womcorp/wom-digital/miwom/charts/miwom-ingress-clone-1/-/jobs/3205558476] |
# h3. WomCorp / WordOfMouth / artifacts / charts / miwom-mesh [git|https://gitlab.com/womcorp/admin/artifacts/charts/miwom-mesh]
    Last pipeline  [623351732|https://gitlab.com/womcorp/admin/artifacts/charts/miwom-mesh/-/pipelines/623351732] on main as success
    Stages :
    ||Stage||link||
    | build (generate_chart) | [2937182154|https://gitlab.com/womcorp/admin/artifacts/charts/miwom-mesh/-/jobs/2937182154] |
# h3. WomCorp / wom-digital / forks / miwom-dev1 / miwom / miwom-documents-ms [git|https://gitlab.com/womcorp/wom-digital/forks/miwom-dev1/miwom/miwom-documents-ms]
    Last pipeline  [676549971|https://gitlab.com/womcorp/wom-digital/forks/miwom-dev1/miwom/miwom-documents-ms/-/pipelines/676549971] on develop as success
    Stages :
    ||Stage||link||
    | tagging (release-tag) | [3224785459|https://gitlab.com/womcorp/wom-digital/forks/miwom-dev1/miwom/miwom-documents-ms/-/jobs/3224785459] |
    | deploy (deploy-dev1) | [3224785457|https://gitlab.com/womcorp/wom-digital/forks/miwom-dev1/miwom/miwom-documents-ms/-/jobs/3224785457] |
    | deploy (deploy-secrets-dev1) | [3224785455|https://gitlab.com/womcorp/wom-digital/forks/miwom-dev1/miwom/miwom-documents-ms/-/jobs/3224785455] |
    | secure (sast) | [3224785451|https://gitlab.com/womcorp/wom-digital/forks/miwom-dev1/miwom/miwom-documents-ms/-/jobs/3224785451] |
    | sonar (sonar) | [3224785448|https://gitlab.com/womcorp/wom-digital/forks/miwom-dev1/miwom/miwom-documents-ms/-/jobs/3224785448] |
    | sonar (eslint) | [3224785446|https://gitlab.com/womcorp/wom-digital/forks/miwom-dev1/miwom/miwom-documents-ms/-/jobs/3224785446] |
    | unit-tests (unit-tests) | [3224785444|https://gitlab.com/womcorp/wom-digital/forks/miwom-dev1/miwom/miwom-documents-ms/-/jobs/3224785444] |
    | build (build-container) | [3224785442|https://gitlab.com/womcorp/wom-digital/forks/miwom-dev1/miwom/miwom-documents-ms/-/jobs/3224785442] |
    | version (version) | [3224785440|https://gitlab.com/womcorp/wom-digital/forks/miwom-dev1/miwom/miwom-documents-ms/-/jobs/3224785440] |
# h3. WomCorp / wom-bi / Analytics and Innovation / Analytics Data Platforms / GCP / EDMS / Data Warehouse / Wom Airflow [git|https://gitlab.com/womcorp/wom-bi/analytics-innovation/analytics-data-platforms/gcp/edms/data-warehouse/wom-airflow]
    Last pipeline  [659406011|https://gitlab.com/womcorp/wom-bi/analytics-innovation/analytics-data-platforms/gcp/edms/data-warehouse/wom-airflow/-/pipelines/659406011] on develop as success
    Stages :
    ||Stage||link||
    | apply (apply) | [3132896012|https://gitlab.com/womcorp/wom-bi/analytics-innovation/analytics-data-platforms/gcp/edms/data-warehouse/wom-airflow/-/jobs/3132896012] |
    | plan (plan) | [3132896010|https://gitlab.com/womcorp/wom-bi/analytics-innovation/analytics-data-platforms/gcp/edms/data-warehouse/wom-airflow/-/jobs/3132896010] |
# h3. WomCorp / wom-digital / womtv / wom-ott-invoices-ms [git|https://gitlab.com/womcorp/wom-digital/womtv/wom-ott-invoices-ms]
    Last pipeline  [676662106|https://gitlab.com/womcorp/wom-digital/womtv/wom-ott-invoices-ms/-/pipelines/676662106] on qa as success
    Stages :
    ||Stage||link||
    | deploy (deploy-qa) | [3225451174|https://gitlab.com/womcorp/wom-digital/womtv/wom-ott-invoices-ms/-/jobs/3225451174] |
    | deploy (deploy-secrets-qa) | [3225451172|https://gitlab.com/womcorp/wom-digital/womtv/wom-ott-invoices-ms/-/jobs/3225451172] |
    | secure (gitleaks) | [3225451170|https://gitlab.com/womcorp/wom-digital/womtv/wom-ott-invoices-ms/-/jobs/3225451170] |
    | verify (yamllint) | [3225451169|https://gitlab.com/womcorp/wom-digital/womtv/wom-ott-invoices-ms/-/jobs/3225451169] |
    | version (version) | [3225451168|https://gitlab.com/womcorp/wom-digital/womtv/wom-ott-invoices-ms/-/jobs/3225451168] |
# h3. WomCorp / wom-digital / miwom / charts / miwom-ingress [git|https://gitlab.com/womcorp/wom-digital/miwom/charts/miwom-ingress]
    Last pipeline  [620019484|https://gitlab.com/womcorp/wom-digital/miwom/charts/miwom-ingress/-/pipelines/620019484] on main as success
    Stages :
    ||Stage||link||
    | deploy (deploy) | [2918575093|https://gitlab.com/womcorp/wom-digital/miwom/charts/miwom-ingress/-/jobs/2918575093] |
    | deploy (deploy-uat) | [2918575092|https://gitlab.com/womcorp/wom-digital/miwom/charts/miwom-ingress/-/jobs/2918575092] |
    | deploy (deploy-qa) | [2918575091|https://gitlab.com/womcorp/wom-digital/miwom/charts/miwom-ingress/-/jobs/2918575091] |
    | deploy (deploy-dev) | [2918575090|https://gitlab.com/womcorp/wom-digital/miwom/charts/miwom-ingress/-/jobs/2918575090] |
    | verify (helm-lint) | [2918575089|https://gitlab.com/womcorp/wom-digital/miwom/charts/miwom-ingress/-/jobs/2918575089] |
# h3. WomCorp / WordOfMouth / artifacts / charts / wom-shared-ingress [git|https://gitlab.com/womcorp/admin/artifacts/charts/wom-shared-ingress]
    Last pipeline  [623351908|https://gitlab.com/womcorp/admin/artifacts/charts/wom-shared-ingress/-/pipelines/623351908] on main as success
    Stages :
    ||Stage||link||
    | build (generate_chart) | [2937183681|https://gitlab.com/womcorp/admin/artifacts/charts/wom-shared-ingress/-/jobs/2937183681] |
# h3. WomCorp / WordOfMouth / artifacts / charts / miwom-base [git|https://gitlab.com/womcorp/admin/artifacts/charts/miwom-base]
    Last pipeline  [623350481|https://gitlab.com/womcorp/admin/artifacts/charts/miwom-base/-/pipelines/623350481] on main as success
    Stages :
    ||Stage||link||
    | build (generate_chart) | [2937174837|https://gitlab.com/womcorp/admin/artifacts/charts/miwom-base/-/jobs/2937174837] |
# h3. WomCorp / wom-digital / miwom / devops / miwom-runners [git|https://gitlab.com/womcorp/wom-digital/miwom/devops/miwom-runners]
# h3. WomCorp / wom-digital / ecommerce / front / gatsby / gatsby-theme-wom-smartphones [git|https://gitlab.com/womcorp/wom-digital/ecommerce/front/gatsby/gatsby-theme-wom-smartphones]
    Last pipeline  [676509007|https://gitlab.com/womcorp/wom-digital/ecommerce/front/gatsby/gatsby-theme-wom-smartphones/-/pipelines/676509007] on int as success
    Stages :
    ||Stage||link||
    | tagging (release-tag) | [3224536551|https://gitlab.com/womcorp/wom-digital/ecommerce/front/gatsby/gatsby-theme-wom-smartphones/-/jobs/3224536551] |
    | compile (build_package) | [3224536549|https://gitlab.com/womcorp/wom-digital/ecommerce/front/gatsby/gatsby-theme-wom-smartphones/-/jobs/3224536549] |
    | version (version) | [3224536546|https://gitlab.com/womcorp/wom-digital/ecommerce/front/gatsby/gatsby-theme-wom-smartphones/-/jobs/3224536546] |
# h3. WomCorp / wom-digital / ecommerce / front / gatsby / theme-wom-smartphones [git|https://gitlab.com/womcorp/wom-digital/ecommerce/front/gatsby/theme-wom-smartphones]
# h3. WomCorp / wom-digital / ecommerce / front / gatsby / gatsby-theme-wom-plp-smartphones [git|https://gitlab.com/womcorp/wom-digital/ecommerce/front/gatsby/gatsby-theme-wom-plp-smartphones]
# h3. WomCorp / wom-digital / core / auth / miwom-tmf-bff [git|https://gitlab.com/womcorp/wom-digital/core/auth/miwom-tmf-bff]
    Last pipeline  [639382272|https://gitlab.com/womcorp/wom-digital/core/auth/miwom-tmf-bff/-/pipelines/639382272] on main as success
    Stages :
    ||Stage||link||
    | deploy (deploy-prod) | [3024077465|https://gitlab.com/womcorp/wom-digital/core/auth/miwom-tmf-bff/-/jobs/3024077465] |
    | secure (gitleaks) | [3024077464|https://gitlab.com/womcorp/wom-digital/core/auth/miwom-tmf-bff/-/jobs/3024077464] |
    | verify (yamllint) | [3024077463|https://gitlab.com/womcorp/wom-digital/core/auth/miwom-tmf-bff/-/jobs/3024077463] |
# h3. WomCorp / wom-digital / core / auth / wom-sso-autologin-ms [git|https://gitlab.com/womcorp/wom-digital/core/auth/wom-sso-autologin-ms]
    Last pipeline  [639382358|https://gitlab.com/womcorp/wom-digital/core/auth/wom-sso-autologin-ms/-/pipelines/639382358] on main as success
    Stages :
    ||Stage||link||
    | deploy (deploy-prod) | [3024078138|https://gitlab.com/womcorp/wom-digital/core/auth/wom-sso-autologin-ms/-/jobs/3024078138] |
    | deploy (deploy-secrets-prod) | [3024078137|https://gitlab.com/womcorp/wom-digital/core/auth/wom-sso-autologin-ms/-/jobs/3024078137] |
    | secure (gitleaks) | [3024078136|https://gitlab.com/womcorp/wom-digital/core/auth/wom-sso-autologin-ms/-/jobs/3024078136] |
    | verify (yamllint) | [3024078135|https://gitlab.com/womcorp/wom-digital/core/auth/wom-sso-autologin-ms/-/jobs/3024078135] |
# h3. WomCorp / WordOfMouth / devops / aws / tgw-miwom-ecomm [git|https://gitlab.com/womcorp/admin/devops/aws/tgw-miwom-ecomm]
# h3. WomCorp / wom-digital / womtv / wom-auth-ms [git|https://gitlab.com/womcorp/wom-digital/womtv/wom-auth-ms]
# h3. WomCorp / wom-digital / womtv / wom-billing-ms [git|https://gitlab.com/womcorp/wom-digital/womtv/wom-billing-ms]
# h3. WomCorp / wom-digital / womtv / wom-payment-ms [git|https://gitlab.com/womcorp/wom-digital/womtv/wom-payment-ms]
# h3. WomCorp / wom-digital / womtv / wom-delivery-ms [git|https://gitlab.com/womcorp/wom-digital/womtv/wom-delivery-ms]
# h3. WomCorp / wom-digital / womtv / wom-product-ms [git|https://gitlab.com/womcorp/wom-digital/womtv/wom-product-ms]
# h3. WomCorp / wom-digital / womtv / wom-ott-customer-ms [git|https://gitlab.com/womcorp/wom-digital/womtv/wom-ott-customer-ms]
    Last pipeline  [687736910|https://gitlab.com/womcorp/wom-digital/womtv/wom-ott-customer-ms/-/pipelines/687736910] on refs/merge-requests/59/head as success
    Stages :
    ||Stage||link||
    | sonar (eslint) | [3285494422|https://gitlab.com/womcorp/wom-digital/womtv/wom-ott-customer-ms/-/jobs/3285494422] |
    | unit-tests (unit-tests) | [3285494420|https://gitlab.com/womcorp/wom-digital/womtv/wom-ott-customer-ms/-/jobs/3285494420] |
# h3. WomCorp / wom-digital / miwom / tools / wom-contentful-migration-tool [git|https://gitlab.com/womcorp/wom-digital/miwom/tools/wom-contentful-migration-tool]
    Last pipeline  [477993622|https://gitlab.com/womcorp/wom-digital/miwom/tools/wom-contentful-migration-tool/-/pipelines/477993622] on main as success
    Stages :
    ||Stage||link||
    | test (semgrep-sast) | [2129464594|https://gitlab.com/womcorp/wom-digital/miwom/tools/wom-contentful-migration-tool/-/jobs/2129464594] |
    | test (nodejs-scan-sast) | [2129464591|https://gitlab.com/womcorp/wom-digital/miwom/tools/wom-contentful-migration-tool/-/jobs/2129464591] |
    | test (eslint-sast) | [2129464589|https://gitlab.com/womcorp/wom-digital/miwom/tools/wom-contentful-migration-tool/-/jobs/2129464589] |
# h3. WomCorp / wom-digital / core / auth / wom-sso-login [git|https://gitlab.com/womcorp/wom-digital/core/auth/wom-sso-login]
    Last pipeline  [691430355|https://gitlab.com/womcorp/wom-digital/core/auth/wom-sso-login/-/pipelines/691430355] on PI2SMF2-8-y as success
    Stages :
    ||Stage||link||
    | secure (gitleaks) | [3305980771|https://gitlab.com/womcorp/wom-digital/core/auth/wom-sso-login/-/jobs/3305980771] |
    | verify (yamllint) | [3305980767|https://gitlab.com/womcorp/wom-digital/core/auth/wom-sso-login/-/jobs/3305980767] |
# h3. WomCorp / wom-digital / miwom / wom-miwom-carrier-billing-ms [git|https://gitlab.com/womcorp/wom-digital/miwom/wom-miwom-carrier-billing-ms]
    Last pipeline  [687899088|https://gitlab.com/womcorp/wom-digital/miwom/wom-miwom-carrier-billing-ms/-/pipelines/687899088] on refs/merge-requests/24/head as success
    Stages :
    ||Stage||link||
    | sonar (eslint) | [3286432056|https://gitlab.com/womcorp/wom-digital/miwom/wom-miwom-carrier-billing-ms/-/jobs/3286432056] |
    | unit-tests (unit-tests) | [3286432053|https://gitlab.com/womcorp/wom-digital/miwom/wom-miwom-carrier-billing-ms/-/jobs/3286432053] |
# h3. WomCorp / wom-digital / womtv / wom-ott-utils [git|https://gitlab.com/womcorp/wom-digital/womtv/wom-ott-utils]
    Last pipeline  [455000009|https://gitlab.com/womcorp/wom-digital/womtv/wom-ott-utils/-/pipelines/455000009] on main as success
    Stages :
    ||Stage||link||
    | deploy (publish) | [2008869343|https://gitlab.com/womcorp/wom-digital/womtv/wom-ott-utils/-/jobs/2008869343] |
    | build (create_npmrc) | [2008869340|https://gitlab.com/womcorp/wom-digital/womtv/wom-ott-utils/-/jobs/2008869340] |
# h3. WomCorp / WordOfMouth / stack / openshift / edms [git|https://gitlab.com/womcorp/admin/stack/openshift/edms]
# h3. WomCorp / wom-bi / kafka-edms / producer-kconnect-wom-fuse-ussd [git|https://gitlab.com/womcorp/wom-bi/kafka-edms/producer-kconnect-wom-fuse-ussd]
# h3. WomCorp / wom-bi / kafka-edms / producer-kconnect-wom-fuse-tapin [git|https://gitlab.com/womcorp/wom-bi/kafka-edms/producer-kconnect-wom-fuse-tapin]
# h3. WomCorp / wom-bi / kafka-edms / producer-kconnect-wom-fuse-smssym [git|https://gitlab.com/womcorp/wom-bi/kafka-edms/producer-kconnect-wom-fuse-smssym]
# h3. WomCorp / wom-bi / kafka-edms / producer-kconnect-wom-fuse-smsshrtc [git|https://gitlab.com/womcorp/wom-bi/kafka-edms/producer-kconnect-wom-fuse-smsshrtc]
# h3. WomCorp / wom-bi / kafka-edms / producer-kconnect-wom-fuse-sgwpa [git|https://gitlab.com/womcorp/wom-bi/kafka-edms/producer-kconnect-wom-fuse-sgwpa]
# h3. WomCorp / wom-bi / kafka-edms / producer-kconnect-wom-fuse-ocstasado [git|https://gitlab.com/womcorp/wom-bi/kafka-edms/producer-kconnect-wom-fuse-ocstasado]
# h3. WomCorp / wom-bi / kafka-edms / producer-kconnect-wom-fuse-ggsnpa [git|https://gitlab.com/womcorp/wom-bi/kafka-edms/producer-kconnect-wom-fuse-ggsnpa]
# h3. WomCorp / wom-digital / miwom / ui / wom-miwom-ui-kit [git|https://gitlab.com/womcorp/wom-digital/miwom/ui/wom-miwom-ui-kit]
    Last pipeline  [683881662|https://gitlab.com/womcorp/wom-digital/miwom/ui/wom-miwom-ui-kit/-/pipelines/683881662] on develop as success
    Stages :
    ||Stage||link||
    | deploy (publish_package) | [3265045022|https://gitlab.com/womcorp/wom-digital/miwom/ui/wom-miwom-ui-kit/-/jobs/3265045022] |
    | build (create_npmrc) | [3265045021|https://gitlab.com/womcorp/wom-digital/miwom/ui/wom-miwom-ui-kit/-/jobs/3265045021] |
    | build (validate_package_scope) | [3265045020|https://gitlab.com/womcorp/wom-digital/miwom/ui/wom-miwom-ui-kit/-/jobs/3265045020] |
# h3. WomCorp / wom-digital / miwom / wom-miwom-tracking-ms [git|https://gitlab.com/womcorp/wom-digital/miwom/wom-miwom-tracking-ms]
    Last pipeline  [687939802|https://gitlab.com/womcorp/wom-digital/miwom/wom-miwom-tracking-ms/-/pipelines/687939802] on refs/merge-requests/57/head as success
    Stages :
    ||Stage||link||
    | sonar (eslint) | [3286673541|https://gitlab.com/womcorp/wom-digital/miwom/wom-miwom-tracking-ms/-/jobs/3286673541] |
    | unit-tests (unit-tests) | [3286673539|https://gitlab.com/womcorp/wom-digital/miwom/wom-miwom-tracking-ms/-/jobs/3286673539] |
# h3. WomCorp / wom-digital / womtv / wom-ott-maintenance [git|https://gitlab.com/womcorp/wom-digital/womtv/wom-ott-maintenance]
    Last pipeline  [412326778|https://gitlab.com/womcorp/wom-digital/womtv/wom-ott-maintenance/-/pipelines/412326778] on qa as success
    Stages :
    ||Stage||link||
    | sync (sync-qa) | [1799856741|https://gitlab.com/womcorp/wom-digital/womtv/wom-ott-maintenance/-/jobs/1799856741] |
    | secure (gitleaks) | [1799856731|https://gitlab.com/womcorp/wom-digital/womtv/wom-ott-maintenance/-/jobs/1799856731] |
    | verify (yamllint) | [1799856729|https://gitlab.com/womcorp/wom-digital/womtv/wom-ott-maintenance/-/jobs/1799856729] |
# h3. WomCorp / wom-bi / kafka-edms / wom-fuse-ussd [git|https://gitlab.com/womcorp/wom-bi/kafka-edms/wom-fuse-ussd]
    Last pipeline  [487491670|https://gitlab.com/womcorp/wom-bi/kafka-edms/wom-fuse-ussd/-/pipelines/487491670] on main as success
    Stages :
    ||Stage||link||
    | deploy_latest (deploy_prod) | [2178873179|https://gitlab.com/womcorp/wom-bi/kafka-edms/wom-fuse-ussd/-/jobs/2178873179] |
# h3. WomCorp / wom-bi / kafka-edms / wom-fuse-tapin [git|https://gitlab.com/womcorp/wom-bi/kafka-edms/wom-fuse-tapin]
    Last pipeline  [410253442|https://gitlab.com/womcorp/wom-bi/kafka-edms/wom-fuse-tapin/-/pipelines/410253442] on desarrollo as success
    Stages :
    ||Stage||link||
    | deploy_latest (deploy_dev) | [1789728845|https://gitlab.com/womcorp/wom-bi/kafka-edms/wom-fuse-tapin/-/jobs/1789728845] |
    | build (docker) | [1789728843|https://gitlab.com/womcorp/wom-bi/kafka-edms/wom-fuse-tapin/-/jobs/1789728843] |
    | compile (compile) | [1789728840|https://gitlab.com/womcorp/wom-bi/kafka-edms/wom-fuse-tapin/-/jobs/1789728840] |
# h3. WomCorp / wom-bi / kafka-edms / wom-fuse-smsshrtc [git|https://gitlab.com/womcorp/wom-bi/kafka-edms/wom-fuse-smsshrtc]
# h3. WomCorp / wom-bi / kafka-edms / wom-fuse-smssym [git|https://gitlab.com/womcorp/wom-bi/kafka-edms/wom-fuse-smssym]
# h3. WomCorp / wom-bi / kafka-edms / wom-fuse-ggsnpa [git|https://gitlab.com/womcorp/wom-bi/kafka-edms/wom-fuse-ggsnpa]
    Last pipeline  [410248145|https://gitlab.com/womcorp/wom-bi/kafka-edms/wom-fuse-ggsnpa/-/pipelines/410248145] on desarrollo as success
    Stages :
    ||Stage||link||
    | deploy_latest (deploy_dev) | [1789696505|https://gitlab.com/womcorp/wom-bi/kafka-edms/wom-fuse-ggsnpa/-/jobs/1789696505] |
    | build (docker) | [1789696501|https://gitlab.com/womcorp/wom-bi/kafka-edms/wom-fuse-ggsnpa/-/jobs/1789696501] |
    | compile (compile) | [1789696497|https://gitlab.com/womcorp/wom-bi/kafka-edms/wom-fuse-ggsnpa/-/jobs/1789696497] |
# h3. WomCorp / wom-bi / kafka-edms / wom-fuse-sgwpa [git|https://gitlab.com/womcorp/wom-bi/kafka-edms/wom-fuse-sgwpa]
    Last pipeline  [410228807|https://gitlab.com/womcorp/wom-bi/kafka-edms/wom-fuse-sgwpa/-/pipelines/410228807] on desarrollo as success
    Stages :
    ||Stage||link||
    | deploy_latest (deploy_dev) | [1789595760|https://gitlab.com/womcorp/wom-bi/kafka-edms/wom-fuse-sgwpa/-/jobs/1789595760] |
    | build (docker) | [1789595758|https://gitlab.com/womcorp/wom-bi/kafka-edms/wom-fuse-sgwpa/-/jobs/1789595758] |
    | compile (compile) | [1789595757|https://gitlab.com/womcorp/wom-bi/kafka-edms/wom-fuse-sgwpa/-/jobs/1789595757] |
# h3. WomCorp / wom-bi / kafka-edms / wom-fuse-aaa [git|https://gitlab.com/womcorp/wom-bi/kafka-edms/wom-fuse-aaa]
    Last pipeline  [484072936|https://gitlab.com/womcorp/wom-bi/kafka-edms/wom-fuse-aaa/-/pipelines/484072936] on main as success
    Stages :
    ||Stage||link||
    | deploy_latest (deploy_prod) | [2161099954|https://gitlab.com/womcorp/wom-bi/kafka-edms/wom-fuse-aaa/-/jobs/2161099954] |
# h3. WomCorp / wom-bi / kafka-edms / wom-fuse-ocstasado [git|https://gitlab.com/womcorp/wom-bi/kafka-edms/wom-fuse-ocstasado]
    Last pipeline  [606433092|https://gitlab.com/womcorp/wom-bi/kafka-edms/wom-fuse-ocstasado/-/pipelines/606433092] on main as success
    Stages :
    ||Stage||link||
    | deploy_latest (deploy_prod) | [2823049638|https://gitlab.com/womcorp/wom-bi/kafka-edms/wom-fuse-ocstasado/-/jobs/2823049638] |
# h3. WomCorp / wom-bi / Analytics and Innovation / Analytics Data Platforms / GCP / Wom MicroStrategy / WOM MSTR Infrastructure [git|https://gitlab.com/womcorp/wom-bi/analytics-innovation/analytics-data-platforms/gcp/wom-mstr/wom-mstr-infrastructure]
    Last pipeline  [673597938|https://gitlab.com/womcorp/wom-bi/analytics-innovation/analytics-data-platforms/gcp/wom-mstr/wom-mstr-infrastructure/-/pipelines/673597938] on master as success
    Stages :
    ||Stage||link||
    | apply (apply prod) | [3210538254|https://gitlab.com/womcorp/wom-bi/analytics-innovation/analytics-data-platforms/gcp/wom-mstr/wom-mstr-infrastructure/-/jobs/3210538254] |
    | apply (apply dev) | [3210538253|https://gitlab.com/womcorp/wom-bi/analytics-innovation/analytics-data-platforms/gcp/wom-mstr/wom-mstr-infrastructure/-/jobs/3210538253] |
    | preflight checks (preflight prod) | [3210538252|https://gitlab.com/womcorp/wom-bi/analytics-innovation/analytics-data-platforms/gcp/wom-mstr/wom-mstr-infrastructure/-/jobs/3210538252] |
    | preflight checks (preflight dev) | [3210538250|https://gitlab.com/womcorp/wom-bi/analytics-innovation/analytics-data-platforms/gcp/wom-mstr/wom-mstr-infrastructure/-/jobs/3210538250] |
# h3. WomCorp / wom-bi / Analytics and Innovation / Analytics Data Platforms / GCP / Wom MicroStrategy / Wom MSTR Infraestructure [git|https://gitlab.com/womcorp/wom-bi/analytics-innovation/analytics-data-platforms/gcp/wom-mstr/wom-mstr-infraestructure]
# h3. WomCorp / WordOfMouth / reuse / tools / miwom-bump-version [git|https://gitlab.com/womcorp/admin/reuse/tools/miwom-bump-version]
# h3. WomCorp / wom-bi / Analytics and Innovation / Analytics Data Platforms / GCP / Wom EDMS Sandbox / Wom Sandbox Edms Datawarehouse [git|https://gitlab.com/womcorp/wom-bi/analytics-innovation/analytics-data-platforms/gcp/wom-edms-sandbox/wom-sandbox-edms-datawarehouse]
    Last pipeline  [507136488|https://gitlab.com/womcorp/wom-bi/analytics-innovation/analytics-data-platforms/gcp/wom-edms-sandbox/wom-sandbox-edms-datawarehouse/-/pipelines/507136488] on refs/merge-requests/74/head as success
    Stages :
    ||Stage||link||
    | stage (secret_detection) | [2282086256|https://gitlab.com/womcorp/wom-bi/analytics-innovation/analytics-data-platforms/gcp/wom-edms-sandbox/wom-sandbox-edms-datawarehouse/-/jobs/2282086256] |
    | stage (semgrep-sast) | [2282086255|https://gitlab.com/womcorp/wom-bi/analytics-innovation/analytics-data-platforms/gcp/wom-edms-sandbox/wom-sandbox-edms-datawarehouse/-/jobs/2282086255] |
    | stage (bandit-sast) | [2282086253|https://gitlab.com/womcorp/wom-bi/analytics-innovation/analytics-data-platforms/gcp/wom-edms-sandbox/wom-sandbox-edms-datawarehouse/-/jobs/2282086253] |
    | stage (code_quality) | [2282086251|https://gitlab.com/womcorp/wom-bi/analytics-innovation/analytics-data-platforms/gcp/wom-edms-sandbox/wom-sandbox-edms-datawarehouse/-/jobs/2282086251] |
    | test (dags_integrity) | [2282086250|https://gitlab.com/womcorp/wom-bi/analytics-innovation/analytics-data-platforms/gcp/wom-edms-sandbox/wom-sandbox-edms-datawarehouse/-/jobs/2282086250] |
    | pre-pipeline (cicd_testing_image_build_push) | [2282086248|https://gitlab.com/womcorp/wom-bi/analytics-innovation/analytics-data-platforms/gcp/wom-edms-sandbox/wom-sandbox-edms-datawarehouse/-/jobs/2282086248] |
# h3. WomCorp / wom-bi / Analytics and Innovation / Analytics Data Platforms / GCP / Wom EDMS Sandbox / Wom Edms Sandbox Infraestructure [git|https://gitlab.com/womcorp/wom-bi/analytics-innovation/analytics-data-platforms/gcp/wom-edms-sandbox/wom-edms-sandbox-infraestructure]
    Last pipeline  [562727382|https://gitlab.com/womcorp/wom-bi/analytics-innovation/analytics-data-platforms/gcp/wom-edms-sandbox/wom-edms-sandbox-infraestructure/-/pipelines/562727382] on master as success
    Stages :
    ||Stage||link||
    | apply (apply sandbox) | [2584438552|https://gitlab.com/womcorp/wom-bi/analytics-innovation/analytics-data-platforms/gcp/wom-edms-sandbox/wom-edms-sandbox-infraestructure/-/jobs/2584438552] |
    | preflight checks (preflight sandbox) | [2584438550|https://gitlab.com/womcorp/wom-bi/analytics-innovation/analytics-data-platforms/gcp/wom-edms-sandbox/wom-edms-sandbox-infraestructure/-/jobs/2584438550] |
# h3. WomCorp / wom-bi / Analytics and Innovation / Cognitive Team / Wom ML [git|https://gitlab.com/womcorp/wom-bi/analytics-innovation/cognitive-team/wom-ml]
    Last pipeline  [343127712|https://gitlab.com/womcorp/wom-bi/analytics-innovation/cognitive-team/wom-ml/-/pipelines/343127712] on main as success
    Stages :
    ||Stage||link||
    | publish (publish_package) | [1453319223|https://gitlab.com/womcorp/wom-bi/analytics-innovation/cognitive-team/wom-ml/-/jobs/1453319223] |
    | deploy (push_to_repository) | [1453319222|https://gitlab.com/womcorp/wom-bi/analytics-innovation/cognitive-team/wom-ml/-/jobs/1453319222] |
    | build (create_class_definitions) | [1453319219|https://gitlab.com/womcorp/wom-bi/analytics-innovation/cognitive-team/wom-ml/-/jobs/1453319219] |
# h3. WomCorp / wom-bi / Analytics and Innovation / Analytics Data Platforms / NPG Campaing / wom-npg-campaing-datawarehouse [git|https://gitlab.com/womcorp/wom-bi/analytics-innovation/analytics-data-platforms/npg-campaing/wom-npg-campaing-datawarehouse]
    Last pipeline  [558028693|https://gitlab.com/womcorp/wom-bi/analytics-innovation/analytics-data-platforms/npg-campaing/wom-npg-campaing-datawarehouse/-/pipelines/558028693] on develop as success
    Stages :
    ||Stage||link||
    | deploy_dev (deploy_bq_dev) | [2559129202|https://gitlab.com/womcorp/wom-bi/analytics-innovation/analytics-data-platforms/npg-campaing/wom-npg-campaing-datawarehouse/-/jobs/2559129202] |
# h3. WomCorp / wom-bi / Analytics and Innovation / Analytics Data Platforms / NPG Campaing / wom-npg-campaing-ingest [git|https://gitlab.com/womcorp/wom-bi/analytics-innovation/analytics-data-platforms/npg-campaing/wom-npg-campaing-ingest]
# h3. WomCorp / wom-bi / Analytics and Innovation / Analytics Data Platforms / NPG Campaing / wom-npg-campaing-infrastructure [git|https://gitlab.com/womcorp/wom-bi/analytics-innovation/analytics-data-platforms/npg-campaing/wom-npg-campaing-infrastructure]
    Last pipeline  [543576733|https://gitlab.com/womcorp/wom-bi/analytics-innovation/analytics-data-platforms/npg-campaing/wom-npg-campaing-infrastructure/-/pipelines/543576733] on develop as success
    Stages :
    ||Stage||link||
    | apply (apply dev) | [2481861212|https://gitlab.com/womcorp/wom-bi/analytics-innovation/analytics-data-platforms/npg-campaing/wom-npg-campaing-infrastructure/-/jobs/2481861212] |
    | preflight checks (preflight dev) | [2481734675|https://gitlab.com/womcorp/wom-bi/analytics-innovation/analytics-data-platforms/npg-campaing/wom-npg-campaing-infrastructure/-/jobs/2481734675] |
# h3. WomCorp / wom-bi / Analytics and Innovation / Analytics Data Platforms / GCP / wom-edms-ingest [git|https://gitlab.com/womcorp/wom-bi/analytics-innovation/analytics-data-platforms/gcp/wom-edms-ingest]
    Last pipeline  [445958732|https://gitlab.com/womcorp/wom-bi/analytics-innovation/analytics-data-platforms/gcp/wom-edms-ingest/-/pipelines/445958732] on refs/merge-requests/7/head as success
    Stages :
    ||Stage||link||
    | test (code_quality) | [1964347236|https://gitlab.com/womcorp/wom-bi/analytics-innovation/analytics-data-platforms/gcp/wom-edms-ingest/-/jobs/1964347236] |
# h3. WomCorp / wom-bi / Analytics and Innovation / Analytics Data Platforms / GCP / wom-edms-datawarehouse [git|https://gitlab.com/womcorp/wom-bi/analytics-innovation/analytics-data-platforms/gcp/wom-edms-datawarehouse]
    Last pipeline  [589709468|https://gitlab.com/womcorp/wom-bi/analytics-innovation/analytics-data-platforms/gcp/wom-edms-datawarehouse/-/pipelines/589709468] on develop_qa as success
    Stages :
    ||Stage||link||
    | deployment (composer_deployment) | [2731602446|https://gitlab.com/womcorp/wom-bi/analytics-innovation/analytics-data-platforms/gcp/wom-edms-datawarehouse/-/jobs/2731602446] |
# h3. WomCorp / wom-bi / Marketing Analytics / Proyectos / Template PowerPoint WOM en RMarkdown [git|https://gitlab.com/womcorp/wom-bi/marketing-analytics/proyectos/template-powerpoint-wom-en-rmarkdown]
# h3. WomCorp / wom-digital / miwom / infrastructure / miwom-terraform [git|https://gitlab.com/womcorp/wom-digital/miwom/infrastructure/miwom-terraform]
# h3. WomCorp / WordOfMouth / reuse / images / wom-ocp_docker_images-rhscl_nginx-112-rhel7 [git|https://gitlab.com/womcorp/admin/reuse/images/wom-ocp_docker_images-rhscl_nginx-112-rhel7]
# h3. WomCorp / wom-bi / Analytics and Innovation / Analytics Data Platforms / GCP / wom-connectivity [git|https://gitlab.com/womcorp/wom-bi/analytics-innovation/analytics-data-platforms/gcp/wom-connectivity]
    Last pipeline  [470803697|https://gitlab.com/womcorp/wom-bi/analytics-innovation/analytics-data-platforms/gcp/wom-connectivity/-/pipelines/470803697] on master as success
    Stages :
    ||Stage||link||
    | preflight checks (preflight prod) | [2092120238|https://gitlab.com/womcorp/wom-bi/analytics-innovation/analytics-data-platforms/gcp/wom-connectivity/-/jobs/2092120238] |
    | apply (apply prod) | [2092097034|https://gitlab.com/womcorp/wom-bi/analytics-innovation/analytics-data-platforms/gcp/wom-connectivity/-/jobs/2092097034] |
# h3. WomCorp / wom-bi / Analytics and Innovation / Analytics Data Platforms / GCP / wom-edms [git|https://gitlab.com/womcorp/wom-bi/analytics-innovation/analytics-data-platforms/gcp/wom-edms]
    Last pipeline  [691230105|https://gitlab.com/womcorp/wom-bi/analytics-innovation/analytics-data-platforms/gcp/wom-edms/-/pipelines/691230105] on master as success
    Stages :
    ||Stage||link||
    | apply (apply prod) | [3304905737|https://gitlab.com/womcorp/wom-bi/analytics-innovation/analytics-data-platforms/gcp/wom-edms/-/jobs/3304905737] |
    | apply (apply qa) | [3304905736|https://gitlab.com/womcorp/wom-bi/analytics-innovation/analytics-data-platforms/gcp/wom-edms/-/jobs/3304905736] |
    | apply (apply dev) | [3304905733|https://gitlab.com/womcorp/wom-bi/analytics-innovation/analytics-data-platforms/gcp/wom-edms/-/jobs/3304905733] |
    | preflight checks (preflight prod) | [3304905731|https://gitlab.com/womcorp/wom-bi/analytics-innovation/analytics-data-platforms/gcp/wom-edms/-/jobs/3304905731] |
    | preflight checks (preflight qa) | [3304905728|https://gitlab.com/womcorp/wom-bi/analytics-innovation/analytics-data-platforms/gcp/wom-edms/-/jobs/3304905728] |
    | preflight checks (preflight dev) | [3304905725|https://gitlab.com/womcorp/wom-bi/analytics-innovation/analytics-data-platforms/gcp/wom-edms/-/jobs/3304905725] |
# h3. WomCorp / wom-bi / Analytics and Innovation / Analytics Data Platforms / GCP / wom-host [git|https://gitlab.com/womcorp/wom-bi/analytics-innovation/analytics-data-platforms/gcp/wom-host]
    Last pipeline  [673600087|https://gitlab.com/womcorp/wom-bi/analytics-innovation/analytics-data-platforms/gcp/wom-host/-/pipelines/673600087] on master as success
    Stages :
    ||Stage||link||
    | apply (apply prod) | [3210551569|https://gitlab.com/womcorp/wom-bi/analytics-innovation/analytics-data-platforms/gcp/wom-host/-/jobs/3210551569] |
    | apply (apply qa) | [3210551568|https://gitlab.com/womcorp/wom-bi/analytics-innovation/analytics-data-platforms/gcp/wom-host/-/jobs/3210551568] |
    | apply (apply dev) | [3210551567|https://gitlab.com/womcorp/wom-bi/analytics-innovation/analytics-data-platforms/gcp/wom-host/-/jobs/3210551567] |
    | preflight checks (preflight prod) | [3210551566|https://gitlab.com/womcorp/wom-bi/analytics-innovation/analytics-data-platforms/gcp/wom-host/-/jobs/3210551566] |
    | preflight checks (preflight qa) | [3210551565|https://gitlab.com/womcorp/wom-bi/analytics-innovation/analytics-data-platforms/gcp/wom-host/-/jobs/3210551565] |
    | preflight checks (preflight dev) | [3210551564|https://gitlab.com/womcorp/wom-bi/analytics-innovation/analytics-data-platforms/gcp/wom-host/-/jobs/3210551564] |
# h3. WomCorp / wom-bi / Analytics and Innovation / Analytics Data Platforms / GCP / wom-gcp [git|https://gitlab.com/womcorp/wom-bi/analytics-innovation/analytics-data-platforms/gcp/wom-gcp]
    Last pipeline  [615070877|https://gitlab.com/womcorp/wom-bi/analytics-innovation/analytics-data-platforms/gcp/wom-gcp/-/pipelines/615070877] on master as success
    Stages :
    ||Stage||link||
    | apply (apply prod) | [2891914596|https://gitlab.com/womcorp/wom-bi/analytics-innovation/analytics-data-platforms/gcp/wom-gcp/-/jobs/2891914596] |
    | apply (apply qa) | [2891914593|https://gitlab.com/womcorp/wom-bi/analytics-innovation/analytics-data-platforms/gcp/wom-gcp/-/jobs/2891914593] |
    | apply (apply dev) | [2891914591|https://gitlab.com/womcorp/wom-bi/analytics-innovation/analytics-data-platforms/gcp/wom-gcp/-/jobs/2891914591] |
    | preflight checks (preflight prod) | [2891914588|https://gitlab.com/womcorp/wom-bi/analytics-innovation/analytics-data-platforms/gcp/wom-gcp/-/jobs/2891914588] |
    | preflight checks (preflight qa) | [2891914586|https://gitlab.com/womcorp/wom-bi/analytics-innovation/analytics-data-platforms/gcp/wom-gcp/-/jobs/2891914586] |
    | preflight checks (preflight dev) | [2891914585|https://gitlab.com/womcorp/wom-bi/analytics-innovation/analytics-data-platforms/gcp/wom-gcp/-/jobs/2891914585] |
    | apply base (apply base) | [2891914583|https://gitlab.com/womcorp/wom-bi/analytics-innovation/analytics-data-platforms/gcp/wom-gcp/-/jobs/2891914583] |
    | preflight base (preflight base) | [2891914581|https://gitlab.com/womcorp/wom-bi/analytics-innovation/analytics-data-platforms/gcp/wom-gcp/-/jobs/2891914581] |
# h3. WomCorp / wom-digital / miwom / wom-miwom-payment-ms [git|https://gitlab.com/womcorp/wom-digital/miwom/wom-miwom-payment-ms]
    Last pipeline  [687902661|https://gitlab.com/womcorp/wom-digital/miwom/wom-miwom-payment-ms/-/pipelines/687902661] on refs/merge-requests/94/head as success
    Stages :
    ||Stage||link||
    | sonar (eslint) | [3286455447|https://gitlab.com/womcorp/wom-digital/miwom/wom-miwom-payment-ms/-/jobs/3286455447] |
    | unit-tests (unit-tests) | [3286455444|https://gitlab.com/womcorp/wom-digital/miwom/wom-miwom-payment-ms/-/jobs/3286455444] |
# h3. WomCorp / wom-bi / Analytics and Innovation / Analytics Data Platforms / Little Requirements / WOM Jira export to BigQuery [git|https://gitlab.com/womcorp/wom-bi/analytics-innovation/analytics-data-platforms/little-requirements/wom-jira-export-to-bigquery]
    Last pipeline  [382511784|https://gitlab.com/womcorp/wom-bi/analytics-innovation/analytics-data-platforms/little-requirements/wom-jira-export-to-bigquery/-/pipelines/382511784] on master as success
    Stages :
    ||Stage||link||
    | bq_export_run (parser_trigger) | [1649329934|https://gitlab.com/womcorp/wom-bi/analytics-innovation/analytics-data-platforms/little-requirements/wom-jira-export-to-bigquery/-/jobs/1649329934] |
# h3. WomCorp / wom-digital / miwom / wom-miwom-documents-ms [git|https://gitlab.com/womcorp/wom-digital/miwom/wom-miwom-documents-ms]
    Last pipeline  [687943963|https://gitlab.com/womcorp/wom-digital/miwom/wom-miwom-documents-ms/-/pipelines/687943963] on refs/merge-requests/56/head as success
    Stages :
    ||Stage||link||
    | sonar (eslint) | [3286699065|https://gitlab.com/womcorp/wom-digital/miwom/wom-miwom-documents-ms/-/jobs/3286699065] |
    | unit-tests (unit-tests) | [3286699063|https://gitlab.com/womcorp/wom-digital/miwom/wom-miwom-documents-ms/-/jobs/3286699063] |
# h3. WomCorp / WordOfMouth / reuse / images / wom-ocp_docker_images-jboss-fuse-6_fis-java-openshift [git|https://gitlab.com/womcorp/admin/reuse/images/wom-ocp_docker_images-jboss-fuse-6_fis-java-openshift]
# h3. WomCorp / wom-bi / Analytics and Innovation / LTDC - Torre de control / Control tower visibility views  [git|https://gitlab.com/womcorp/wom-bi/analytics-innovation/log-torre-de-control/control-tower-visibility-views]
    Last pipeline  [652295615|https://gitlab.com/womcorp/wom-bi/analytics-innovation/log-torre-de-control/control-tower-visibility-views/-/pipelines/652295615] on master as success
    Stages :
    ||Stage||link||
    | documentation (pages) | [3093643265|https://gitlab.com/womcorp/wom-bi/analytics-innovation/log-torre-de-control/control-tower-visibility-views/-/jobs/3093643265] |
    | documentation (pages_build) | [3093643264|https://gitlab.com/womcorp/wom-bi/analytics-innovation/log-torre-de-control/control-tower-visibility-views/-/jobs/3093643264] |
    | deployment_prod (sheets_link) | [3093643263|https://gitlab.com/womcorp/wom-bi/analytics-innovation/log-torre-de-control/control-tower-visibility-views/-/jobs/3093643263] |
    | deployment_prod (deployment_prod) | [3093643262|https://gitlab.com/womcorp/wom-bi/analytics-innovation/log-torre-de-control/control-tower-visibility-views/-/jobs/3093643262] |
# h3. WomCorp / wom-digital / fiber / wom-fiber-auth-ms [git|https://gitlab.com/womcorp/wom-digital/fiber/wom-fiber-auth-ms]
    Last pipeline  [687891324|https://gitlab.com/womcorp/wom-digital/fiber/wom-fiber-auth-ms/-/pipelines/687891324] on refs/merge-requests/125/head as success
    Stages :
    ||Stage||link||
    | sonar (eslint) | [3286380945|https://gitlab.com/womcorp/wom-digital/fiber/wom-fiber-auth-ms/-/jobs/3286380945] |
    | unit-tests (unit-tests) | [3286380944|https://gitlab.com/womcorp/wom-digital/fiber/wom-fiber-auth-ms/-/jobs/3286380944] |
# h3. WomCorp / wom-digital / fiber / wom-fiber-invoices-ms [git|https://gitlab.com/womcorp/wom-digital/fiber/wom-fiber-invoices-ms]
    Last pipeline  [689911103|https://gitlab.com/womcorp/wom-digital/fiber/wom-fiber-invoices-ms/-/pipelines/689911103] on qa as success
    Stages :
    ||Stage||link||
    | deploy (deploy-qa) | [3297531984|https://gitlab.com/womcorp/wom-digital/fiber/wom-fiber-invoices-ms/-/jobs/3297531984] |
    | deploy (deploy-secrets-qa) | [3297531983|https://gitlab.com/womcorp/wom-digital/fiber/wom-fiber-invoices-ms/-/jobs/3297531983] |
    | secure (gitleaks) | [3297531982|https://gitlab.com/womcorp/wom-digital/fiber/wom-fiber-invoices-ms/-/jobs/3297531982] |
    | verify (yamllint) | [3297531981|https://gitlab.com/womcorp/wom-digital/fiber/wom-fiber-invoices-ms/-/jobs/3297531981] |
    | version (version) | [3297531980|https://gitlab.com/womcorp/wom-digital/fiber/wom-fiber-invoices-ms/-/jobs/3297531980] |
# h3. WomCorp / wom-digital / fiber / wom-fiber-customer-ms [git|https://gitlab.com/womcorp/wom-digital/fiber/wom-fiber-customer-ms]
    Last pipeline  [687894019|https://gitlab.com/womcorp/wom-digital/fiber/wom-fiber-customer-ms/-/pipelines/687894019] on refs/merge-requests/138/head as success
    Stages :
    ||Stage||link||
    | sonar (eslint) | [3286395046|https://gitlab.com/womcorp/wom-digital/fiber/wom-fiber-customer-ms/-/jobs/3286395046] |
    | unit-tests (unit-tests) | [3286395045|https://gitlab.com/womcorp/wom-digital/fiber/wom-fiber-customer-ms/-/jobs/3286395045] |
# h3. WomCorp / wom-digital / fiber / wom-fiber-captive-static [git|https://gitlab.com/womcorp/wom-digital/fiber/wom-fiber-captive-static]
# h3. WomCorp / wom-it / mw / wom-certs [git|https://gitlab.com/womcorp/wom-it/mw/wom-certs]
# h3. WomCorp / wom-digital / miwom / wom-shared-maintenance [git|https://gitlab.com/womcorp/wom-digital/miwom/wom-shared-maintenance]
    Last pipeline  [506287355|https://gitlab.com/womcorp/wom-digital/miwom/wom-shared-maintenance/-/pipelines/506287355] on master as success
    Stages :
    ||Stage||link||
    | deploy (deploy) | [2277597489|https://gitlab.com/womcorp/wom-digital/miwom/wom-shared-maintenance/-/jobs/2277597489] |
    | build (build) | [2277597485|https://gitlab.com/womcorp/wom-digital/miwom/wom-shared-maintenance/-/jobs/2277597485] |
    | validate (validate) | [2277597481|https://gitlab.com/womcorp/wom-digital/miwom/wom-shared-maintenance/-/jobs/2277597481] |
    | validate (fmt) | [2277597475|https://gitlab.com/womcorp/wom-digital/miwom/wom-shared-maintenance/-/jobs/2277597475] |
    | verify (yamllint) | [2277597463|https://gitlab.com/womcorp/wom-digital/miwom/wom-shared-maintenance/-/jobs/2277597463] |
# h3. WomCorp / wom-digital / ecommerce / front / gatsby / gatsby-theme-wom-order-tracking [git|https://gitlab.com/womcorp/wom-digital/ecommerce/front/gatsby/gatsby-theme-wom-order-tracking]
    Last pipeline  [675420296|https://gitlab.com/womcorp/wom-digital/ecommerce/front/gatsby/gatsby-theme-wom-order-tracking/-/pipelines/675420296] on int as success
    Stages :
    ||Stage||link||
    | tagging (release-tag) | [3218251963|https://gitlab.com/womcorp/wom-digital/ecommerce/front/gatsby/gatsby-theme-wom-order-tracking/-/jobs/3218251963] |
    | compile (build_package) | [3218251960|https://gitlab.com/womcorp/wom-digital/ecommerce/front/gatsby/gatsby-theme-wom-order-tracking/-/jobs/3218251960] |
    | version (version) | [3218251954|https://gitlab.com/womcorp/wom-digital/ecommerce/front/gatsby/gatsby-theme-wom-order-tracking/-/jobs/3218251954] |
# h3. WomCorp / wom-digital / fiber / wom-fiber-docs [git|https://gitlab.com/womcorp/wom-digital/fiber/wom-fiber-docs]
# h3. WomCorp / wom-digital / miwom / wom-miwom-docs [git|https://gitlab.com/womcorp/wom-digital/miwom/wom-miwom-docs]
# h3. WomCorp / wom-digital / womtv / wom-ott-docs [git|https://gitlab.com/womcorp/wom-digital/womtv/wom-ott-docs]
# h3. WomCorp / wom-digital / fiber / wom-fiber-mtls [git|https://gitlab.com/womcorp/wom-digital/fiber/wom-fiber-mtls]
    Last pipeline  [659442790|https://gitlab.com/womcorp/wom-digital/fiber/wom-fiber-mtls/-/pipelines/659442790] on uat as success
    Stages :
    ||Stage||link||
    | deploy (deploy-uat) | [3133123868|https://gitlab.com/womcorp/wom-digital/fiber/wom-fiber-mtls/-/jobs/3133123868] |
    | build-uat-prod (build-uat-prod) | [3133123867|https://gitlab.com/womcorp/wom-digital/fiber/wom-fiber-mtls/-/jobs/3133123867] |
# h3. WomCorp / wom-digital / womtv / wom-ott-contentful-ms [git|https://gitlab.com/womcorp/wom-digital/womtv/wom-ott-contentful-ms]
    Last pipeline  [683931896|https://gitlab.com/womcorp/wom-digital/womtv/wom-ott-contentful-ms/-/pipelines/683931896] on refs/merge-requests/101/head as success
    Stages :
    ||Stage||link||
    | sonar (eslint) | [3265344900|https://gitlab.com/womcorp/wom-digital/womtv/wom-ott-contentful-ms/-/jobs/3265344900] |
    | unit-tests (unit-tests) | [3265344899|https://gitlab.com/womcorp/wom-digital/womtv/wom-ott-contentful-ms/-/jobs/3265344899] |
# h3. WomCorp / wom-digital / womtv / wom-ott-mtls [git|https://gitlab.com/womcorp/wom-digital/womtv/wom-ott-mtls]
    Last pipeline  [658440808|https://gitlab.com/womcorp/wom-digital/womtv/wom-ott-mtls/-/pipelines/658440808] on master as success
    Stages :
    ||Stage||link||
    | deploy (deploy-prod) | [3127427260|https://gitlab.com/womcorp/wom-digital/womtv/wom-ott-mtls/-/jobs/3127427260] |
    | verify (helm-lint) | [3127427259|https://gitlab.com/womcorp/wom-digital/womtv/wom-ott-mtls/-/jobs/3127427259] |
    | verify (yamllint) | [3127427258|https://gitlab.com/womcorp/wom-digital/womtv/wom-ott-mtls/-/jobs/3127427258] |
# h3. WomCorp / wom-digital / fiber / wom-consumption-ms [git|https://gitlab.com/womcorp/wom-digital/fiber/wom-consumption-ms]
# h3. WomCorp / wom-digital / miwom / wom-miwom-auth-ms [git|https://gitlab.com/womcorp/wom-digital/miwom/wom-miwom-auth-ms]
    Last pipeline  [691043834|https://gitlab.com/womcorp/wom-digital/miwom/wom-miwom-auth-ms/-/pipelines/691043834] on feature/WOMTV-4729 as success
    Stages :
    ||Stage||link||
    | secure (gitleaks) | [3303863752|https://gitlab.com/womcorp/wom-digital/miwom/wom-miwom-auth-ms/-/jobs/3303863752] |
    | verify (yamllint) | [3303863750|https://gitlab.com/womcorp/wom-digital/miwom/wom-miwom-auth-ms/-/jobs/3303863750] |
    | version (version) | [3303863749|https://gitlab.com/womcorp/wom-digital/miwom/wom-miwom-auth-ms/-/jobs/3303863749] |
# h3. WomCorp / wom-digital / miwom / wom-miwom-front [git|https://gitlab.com/womcorp/wom-digital/miwom/wom-miwom-front]
    Last pipeline  [690301564|https://gitlab.com/womcorp/wom-digital/miwom/wom-miwom-front/-/pipelines/690301564] on MIWMOV-4408 as success
    Stages :
    ||Stage||link||
    | secure (gitleaks) | [3299966913|https://gitlab.com/womcorp/wom-digital/miwom/wom-miwom-front/-/jobs/3299966913] |
    | verify (yamllint) | [3299966909|https://gitlab.com/womcorp/wom-digital/miwom/wom-miwom-front/-/jobs/3299966909] |
# h3. WomCorp / wom-digital / ecommerce / front / gatsby / gatsby-theme-wom-login [git|https://gitlab.com/womcorp/wom-digital/ecommerce/front/gatsby/gatsby-theme-wom-login]
    Last pipeline  [362314504|https://gitlab.com/womcorp/wom-digital/ecommerce/front/gatsby/gatsby-theme-wom-login/-/pipelines/362314504] on add-husky as success
    Stages :
    ||Stage||link||
    | tagging (release-tag) | [1548303573|https://gitlab.com/womcorp/wom-digital/ecommerce/front/gatsby/gatsby-theme-wom-login/-/jobs/1548303573] |
    | compile (build_package) | [1548303571|https://gitlab.com/womcorp/wom-digital/ecommerce/front/gatsby/gatsby-theme-wom-login/-/jobs/1548303571] |
    | version (version) | [1548303568|https://gitlab.com/womcorp/wom-digital/ecommerce/front/gatsby/gatsby-theme-wom-login/-/jobs/1548303568] |
# h3. WomCorp / wom-digital / fiber / wom-fiber-front [git|https://gitlab.com/womcorp/wom-digital/fiber/wom-fiber-front]
    Last pipeline  [678804289|https://gitlab.com/womcorp/wom-digital/fiber/wom-fiber-front/-/pipelines/678804289] on qa as success
    Stages :
    ||Stage||link||
    | deploy (deploy-qa) | [3237371836|https://gitlab.com/womcorp/wom-digital/fiber/wom-fiber-front/-/jobs/3237371836] |
    | secure (gitleaks) | [3237371835|https://gitlab.com/womcorp/wom-digital/fiber/wom-fiber-front/-/jobs/3237371835] |
    | verify (yamllint) | [3237371833|https://gitlab.com/womcorp/wom-digital/fiber/wom-fiber-front/-/jobs/3237371833] |
    | build (build-qa) | [3237371830|https://gitlab.com/womcorp/wom-digital/fiber/wom-fiber-front/-/jobs/3237371830] |
# h3. WomCorp / wom-digital / womtv / wom-kyc-ms [git|https://gitlab.com/womcorp/wom-digital/womtv/wom-kyc-ms]
    Last pipeline  [683930271|https://gitlab.com/womcorp/wom-digital/womtv/wom-kyc-ms/-/pipelines/683930271] on refs/merge-requests/122/head as success
    Stages :
    ||Stage||link||
    | sonar (eslint) | [3265335855|https://gitlab.com/womcorp/wom-digital/womtv/wom-kyc-ms/-/jobs/3265335855] |
    | unit-tests (unit-tests) | [3265335853|https://gitlab.com/womcorp/wom-digital/womtv/wom-kyc-ms/-/jobs/3265335853] |
# h3. WomCorp / wom-digital / womtv / wom-credit-score-ms [git|https://gitlab.com/womcorp/wom-digital/womtv/wom-credit-score-ms]
# h3. WomCorp / wom-digital / miwom / wom-miwom-mtls [git|https://gitlab.com/womcorp/wom-digital/miwom/wom-miwom-mtls]
    Last pipeline  [671728811|https://gitlab.com/womcorp/wom-digital/miwom/wom-miwom-mtls/-/pipelines/671728811] on feature/add-dev1 as success
    Stages :
    ||Stage||link||
    | deploy (deploy-dev1) | [3200157444|https://gitlab.com/womcorp/wom-digital/miwom/wom-miwom-mtls/-/jobs/3200157444] |
    | verify (helm-lint) | [3200157443|https://gitlab.com/womcorp/wom-digital/miwom/wom-miwom-mtls/-/jobs/3200157443] |
    | verify (yamllint) | [3200157442|https://gitlab.com/womcorp/wom-digital/miwom/wom-miwom-mtls/-/jobs/3200157442] |
# h3. WomCorp / wom-digital / miwom / wom-miwom-faker-ms [git|https://gitlab.com/womcorp/wom-digital/miwom/wom-miwom-faker-ms]
    Last pipeline  [247181075|https://gitlab.com/womcorp/wom-digital/miwom/wom-miwom-faker-ms/-/pipelines/247181075] on develop as success
    Stages :
    ||Stage||link||
    | deploy (deploy-dev) | [988114365|https://gitlab.com/womcorp/wom-digital/miwom/wom-miwom-faker-ms/-/jobs/988114365] |
    | build (build) | [988114364|https://gitlab.com/womcorp/wom-digital/miwom/wom-miwom-faker-ms/-/jobs/988114364] |
# h3. WomCorp / wom-digital / miwom / wom-miwom-invoices-ms [git|https://gitlab.com/womcorp/wom-digital/miwom/wom-miwom-invoices-ms]
    Last pipeline  [687993271|https://gitlab.com/womcorp/wom-digital/miwom/wom-miwom-invoices-ms/-/pipelines/687993271] on refs/merge-requests/254/head as success
    Stages :
    ||Stage||link||
    | sonar (eslint) | [3286997574|https://gitlab.com/womcorp/wom-digital/miwom/wom-miwom-invoices-ms/-/jobs/3286997574] |
    | unit-tests (unit-tests) | [3286997572|https://gitlab.com/womcorp/wom-digital/miwom/wom-miwom-invoices-ms/-/jobs/3286997572] |
# h3. WomCorp / wom-digital / miwom / wom-miwom-marketplace-ms [git|https://gitlab.com/womcorp/wom-digital/miwom/wom-miwom-marketplace-ms]
    Last pipeline  [685146963|https://gitlab.com/womcorp/wom-digital/miwom/wom-miwom-marketplace-ms/-/pipelines/685146963] on refs/merge-requests/225/head as success
    Stages :
    ||Stage||link||
    | sonar (eslint) | [3272184254|https://gitlab.com/womcorp/wom-digital/miwom/wom-miwom-marketplace-ms/-/jobs/3272184254] |
    | unit-tests (unit-tests) | [3272184249|https://gitlab.com/womcorp/wom-digital/miwom/wom-miwom-marketplace-ms/-/jobs/3272184249] |
# h3. WomCorp / wom-digital / miwom / wom-miwom-customer-ms [git|https://gitlab.com/womcorp/wom-digital/miwom/wom-miwom-customer-ms]
    Last pipeline  [691192443|https://gitlab.com/womcorp/wom-digital/miwom/wom-miwom-customer-ms/-/pipelines/691192443] on refs/merge-requests/491/head as success
    Stages :
    ||Stage||link||
    | sonar (eslint) | [3304618527|https://gitlab.com/womcorp/wom-digital/miwom/wom-miwom-customer-ms/-/jobs/3304618527] |
    | unit-tests (unit-tests) | [3304618524|https://gitlab.com/womcorp/wom-digital/miwom/wom-miwom-customer-ms/-/jobs/3304618524] |
# h3. WomCorp / wom-digital / miwom / wom-miwom-history-ms [git|https://gitlab.com/womcorp/wom-digital/miwom/wom-miwom-history-ms]
    Last pipeline  [687538277|https://gitlab.com/womcorp/wom-digital/miwom/wom-miwom-history-ms/-/pipelines/687538277] on refs/merge-requests/104/head as success
    Stages :
    ||Stage||link||
    | sonar (eslint) | [3284333236|https://gitlab.com/womcorp/wom-digital/miwom/wom-miwom-history-ms/-/jobs/3284333236] |
    | unit-tests (unit-tests) | [3284333227|https://gitlab.com/womcorp/wom-digital/miwom/wom-miwom-history-ms/-/jobs/3284333227] |
# h3. WomCorp / wom-digital / miwom / wom-miwom-contentful-ms [git|https://gitlab.com/womcorp/wom-digital/miwom/wom-contentful-ms]
    Last pipeline  [685065593|https://gitlab.com/womcorp/wom-digital/miwom/wom-contentful-ms/-/pipelines/685065593] on refs/merge-requests/153/head as success
    Stages :
    ||Stage||link||
    | sonar (eslint) | [3271682274|https://gitlab.com/womcorp/wom-digital/miwom/wom-contentful-ms/-/jobs/3271682274] |
    | unit-tests (unit-tests) | [3271682273|https://gitlab.com/womcorp/wom-digital/miwom/wom-contentful-ms/-/jobs/3271682273] |
# h3. WomCorp / wom-digital / womtv / wom-ott-front [git|https://gitlab.com/womcorp/wom-digital/womtv/wom-ott-front]
    Last pipeline  [683963188|https://gitlab.com/womcorp/wom-digital/womtv/wom-ott-front/-/pipelines/683963188] on qa as success
    Stages :
    ||Stage||link||
    | deploy (deploy-qa) | [3265528406|https://gitlab.com/womcorp/wom-digital/womtv/wom-ott-front/-/jobs/3265528406] |
    | secure (gitleaks) | [3265528403|https://gitlab.com/womcorp/wom-digital/womtv/wom-ott-front/-/jobs/3265528403] |
    | verify (yamllint) | [3265528401|https://gitlab.com/womcorp/wom-digital/womtv/wom-ott-front/-/jobs/3265528401] |
    | build (build-qa) | [3265528400|https://gitlab.com/womcorp/wom-digital/womtv/wom-ott-front/-/jobs/3265528400] |
# h3. WomCorp / wom-digital / core / UI / wom-ui-kit [git|https://gitlab.com/womcorp/wom-digital/core/ui/wom-ui-kit]
    Last pipeline  [676759620|https://gitlab.com/womcorp/wom-digital/core/ui/wom-ui-kit/-/pipelines/676759620] on master as success
    Stages :
    ||Stage||link||
    | deploy (publish_package) | [3226056967|https://gitlab.com/womcorp/wom-digital/core/ui/wom-ui-kit/-/jobs/3226056967] |
    | build (create_npmrc) | [3226056965|https://gitlab.com/womcorp/wom-digital/core/ui/wom-ui-kit/-/jobs/3226056965] |
    | build (validate_package_scope) | [3226056964|https://gitlab.com/womcorp/wom-digital/core/ui/wom-ui-kit/-/jobs/3226056964] |
# h3. WomCorp / wom-digital / womtv / wom-ott-mocks-ms [git|https://gitlab.com/womcorp/wom-digital/womtv/wom-ott-mocks-ms]
    Last pipeline  [676507345|https://gitlab.com/womcorp/wom-digital/womtv/wom-ott-mocks-ms/-/pipelines/676507345] on qa as success
    Stages :
    ||Stage||link||
    | deploy (deploy-qa) | [3224525878|https://gitlab.com/womcorp/wom-digital/womtv/wom-ott-mocks-ms/-/jobs/3224525878] |
    | deploy (deploy-secrets-qa) | [3224525875|https://gitlab.com/womcorp/wom-digital/womtv/wom-ott-mocks-ms/-/jobs/3224525875] |
    | secure (gitleaks) | [3224525872|https://gitlab.com/womcorp/wom-digital/womtv/wom-ott-mocks-ms/-/jobs/3224525872] |
    | verify (yamllint) | [3224525869|https://gitlab.com/womcorp/wom-digital/womtv/wom-ott-mocks-ms/-/jobs/3224525869] |
    | version (version) | [3224525867|https://gitlab.com/womcorp/wom-digital/womtv/wom-ott-mocks-ms/-/jobs/3224525867] |
# h3. WomCorp / wom-digital / ecommerce / front / gatsby / gatsby-theme-wom-tmf [git|https://gitlab.com/womcorp/wom-digital/ecommerce/front/gatsby/gatsby-theme-wom-tmf]
    Last pipeline  [675417669|https://gitlab.com/womcorp/wom-digital/ecommerce/front/gatsby/gatsby-theme-wom-tmf/-/pipelines/675417669] on int as success
    Stages :
    ||Stage||link||
    | tagging (release-tag) | [3218235576|https://gitlab.com/womcorp/wom-digital/ecommerce/front/gatsby/gatsby-theme-wom-tmf/-/jobs/3218235576] |
    | compile (build_package) | [3218235574|https://gitlab.com/womcorp/wom-digital/ecommerce/front/gatsby/gatsby-theme-wom-tmf/-/jobs/3218235574] |
    | version (version) | [3218235572|https://gitlab.com/womcorp/wom-digital/ecommerce/front/gatsby/gatsby-theme-wom-tmf/-/jobs/3218235572] |
# h3. WomCorp / wom-digital / ecommerce / front / gatsby / gatsby-theme-wom-search [git|https://gitlab.com/womcorp/wom-digital/ecommerce/front/gatsby/gatsby-theme-wom-search]
# h3. WomCorp / wom-digital / ecommerce / front / gatsby / gatsby-theme-wom-magento [git|https://gitlab.com/womcorp/wom-digital/ecommerce/front/gatsby/gatsby-theme-wom-magento]
    Last pipeline  [689116939|https://gitlab.com/womcorp/wom-digital/ecommerce/front/gatsby/gatsby-theme-wom-magento/-/pipelines/689116939] on int as success
    Stages :
    ||Stage||link||
    | tagging (release-tag) | [3293106406|https://gitlab.com/womcorp/wom-digital/ecommerce/front/gatsby/gatsby-theme-wom-magento/-/jobs/3293106406] |
    | compile (build_package) | [3293106405|https://gitlab.com/womcorp/wom-digital/ecommerce/front/gatsby/gatsby-theme-wom-magento/-/jobs/3293106405] |
    | version (version) | [3293106404|https://gitlab.com/womcorp/wom-digital/ecommerce/front/gatsby/gatsby-theme-wom-magento/-/jobs/3293106404] |
# h3. WomCorp / wom-digital / ecommerce / front / gatsby / gatsby-theme-wom-core [git|https://gitlab.com/womcorp/wom-digital/ecommerce/front/gatsby/gatsby-theme-wom-core]
    Last pipeline  [684905171|https://gitlab.com/womcorp/wom-digital/ecommerce/front/gatsby/gatsby-theme-wom-core/-/pipelines/684905171] on int as success
    Stages :
    ||Stage||link||
    | tagging (release-tag) | [3270826422|https://gitlab.com/womcorp/wom-digital/ecommerce/front/gatsby/gatsby-theme-wom-core/-/jobs/3270826422] |
    | compile (build_package) | [3270826418|https://gitlab.com/womcorp/wom-digital/ecommerce/front/gatsby/gatsby-theme-wom-core/-/jobs/3270826418] |
    | version (version) | [3270826415|https://gitlab.com/womcorp/wom-digital/ecommerce/front/gatsby/gatsby-theme-wom-core/-/jobs/3270826415] |
# h3. WomCorp / wom-digital / ecommerce / front / gatsby / gatsby-theme-wom-contentful [git|https://gitlab.com/womcorp/wom-digital/ecommerce/front/gatsby/gatsby-theme-wom-contentful]
    Last pipeline  [437633438|https://gitlab.com/womcorp/wom-digital/ecommerce/front/gatsby/gatsby-theme-wom-contentful/-/pipelines/437633438] on master as success
    Stages :
    ||Stage||link||
    | tagging (release-tag) | [1923920196|https://gitlab.com/womcorp/wom-digital/ecommerce/front/gatsby/gatsby-theme-wom-contentful/-/jobs/1923920196] |
    | compile (build_package) | [1923920195|https://gitlab.com/womcorp/wom-digital/ecommerce/front/gatsby/gatsby-theme-wom-contentful/-/jobs/1923920195] |
    | version (version) | [1923920194|https://gitlab.com/womcorp/wom-digital/ecommerce/front/gatsby/gatsby-theme-wom-contentful/-/jobs/1923920194] |
# h3. WomCorp / wom-digital / ecommerce / front / gatsby / gatsby-theme-wom-checkout [git|https://gitlab.com/womcorp/wom-digital/ecommerce/front/gatsby/gatsby-theme-wom-checkout]
    Last pipeline  [690463963|https://gitlab.com/womcorp/wom-digital/ecommerce/front/gatsby/gatsby-theme-wom-checkout/-/pipelines/690463963] on master as success
    Stages :
    ||Stage||link||
    | tagging (release-tag) | [3300887453|https://gitlab.com/womcorp/wom-digital/ecommerce/front/gatsby/gatsby-theme-wom-checkout/-/jobs/3300887453] |
    | compile (build_package) | [3300887452|https://gitlab.com/womcorp/wom-digital/ecommerce/front/gatsby/gatsby-theme-wom-checkout/-/jobs/3300887452] |
    | version (version) | [3300887451|https://gitlab.com/womcorp/wom-digital/ecommerce/front/gatsby/gatsby-theme-wom-checkout/-/jobs/3300887451] |
# h3. WomCorp / wom-digital / ecommerce / front / gatsby / gatsby-theme-wom-cart [git|https://gitlab.com/womcorp/wom-digital/ecommerce/front/gatsby/gatsby-theme-wom-cart]
    Last pipeline  [675418637|https://gitlab.com/womcorp/wom-digital/ecommerce/front/gatsby/gatsby-theme-wom-cart/-/pipelines/675418637] on int as success
    Stages :
    ||Stage||link||
    | tagging (release-tag) | [3218241669|https://gitlab.com/womcorp/wom-digital/ecommerce/front/gatsby/gatsby-theme-wom-cart/-/jobs/3218241669] |
    | compile (build_package) | [3218241666|https://gitlab.com/womcorp/wom-digital/ecommerce/front/gatsby/gatsby-theme-wom-cart/-/jobs/3218241666] |
    | version (version) | [3218241664|https://gitlab.com/womcorp/wom-digital/ecommerce/front/gatsby/gatsby-theme-wom-cart/-/jobs/3218241664] |
# h3. WomCorp / wom-digital / ecommerce / front / gatsby / gatsby-theme-wom-browse [git|https://gitlab.com/womcorp/wom-digital/ecommerce/front/gatsby/gatsby-theme-wom-browse]
    Last pipeline  [690463895|https://gitlab.com/womcorp/wom-digital/ecommerce/front/gatsby/gatsby-theme-wom-browse/-/pipelines/690463895] on master as success
    Stages :
    ||Stage||link||
    | tagging (release-tag) | [3300886795|https://gitlab.com/womcorp/wom-digital/ecommerce/front/gatsby/gatsby-theme-wom-browse/-/jobs/3300886795] |
    | compile (build_package) | [3300886794|https://gitlab.com/womcorp/wom-digital/ecommerce/front/gatsby/gatsby-theme-wom-browse/-/jobs/3300886794] |
    | version (version) | [3300886793|https://gitlab.com/womcorp/wom-digital/ecommerce/front/gatsby/gatsby-theme-wom-browse/-/jobs/3300886793] |
# h3. WomCorp / wom-digital / womtv / wom-evergent-ms [git|https://gitlab.com/womcorp/wom-digital/womtv/wom-evergent-ms]
    Last pipeline  [687930528|https://gitlab.com/womcorp/wom-digital/womtv/wom-evergent-ms/-/pipelines/687930528] on develop as success
    Stages :
    ||Stage||link||
    | tagging (release-tag) | [3286613525|https://gitlab.com/womcorp/wom-digital/womtv/wom-evergent-ms/-/jobs/3286613525] |
    | deploy (deploy-dev) | [3286613523|https://gitlab.com/womcorp/wom-digital/womtv/wom-evergent-ms/-/jobs/3286613523] |
    | deploy (deploy-secrets-dev) | [3286613522|https://gitlab.com/womcorp/wom-digital/womtv/wom-evergent-ms/-/jobs/3286613522] |
    | secure (gitleaks) | [3286613521|https://gitlab.com/womcorp/wom-digital/womtv/wom-evergent-ms/-/jobs/3286613521] |
    | secure (sast) | [3286613520|https://gitlab.com/womcorp/wom-digital/womtv/wom-evergent-ms/-/jobs/3286613520] |
    | sonar (sonar) | [3286613519|https://gitlab.com/womcorp/wom-digital/womtv/wom-evergent-ms/-/jobs/3286613519] |
    | sonar (eslint) | [3286613516|https://gitlab.com/womcorp/wom-digital/womtv/wom-evergent-ms/-/jobs/3286613516] |
    | verify (yamllint) | [3286613514|https://gitlab.com/womcorp/wom-digital/womtv/wom-evergent-ms/-/jobs/3286613514] |
    | unit-tests (unit-tests) | [3286613513|https://gitlab.com/womcorp/wom-digital/womtv/wom-evergent-ms/-/jobs/3286613513] |
    | build (build-container) | [3286613512|https://gitlab.com/womcorp/wom-digital/womtv/wom-evergent-ms/-/jobs/3286613512] |
    | version (version) | [3286613511|https://gitlab.com/womcorp/wom-digital/womtv/wom-evergent-ms/-/jobs/3286613511] |
# h3. WomCorp / wom-digital / womtv / wom-wompay-ms [git|https://gitlab.com/womcorp/wom-digital/womtv/wom-wompay-ms]
    Last pipeline  [687977448|https://gitlab.com/womcorp/wom-digital/womtv/wom-wompay-ms/-/pipelines/687977448] on refs/merge-requests/109/head as success
    Stages :
    ||Stage||link||
    | sonar (eslint) | [3286904547|https://gitlab.com/womcorp/wom-digital/womtv/wom-wompay-ms/-/jobs/3286904547] |
    | unit-tests (unit-tests) | [3286904546|https://gitlab.com/womcorp/wom-digital/womtv/wom-wompay-ms/-/jobs/3286904546] |
# h3. WomCorp / wom-digital / core / tmf / libraries / wom-cl-domain [git|https://gitlab.com/womcorp/wom-digital/core/tmf/libraries/wom-cl-domain]
    Last pipeline  [685739169|https://gitlab.com/womcorp/wom-digital/core/tmf/libraries/wom-cl-domain/-/pipelines/685739169] on master as success
    Stages :
    ||Stage||link||
    | tagging (release-tag) | [3275423820|https://gitlab.com/womcorp/wom-digital/core/tmf/libraries/wom-cl-domain/-/jobs/3275423820] |
    | sonar (sonarqube) | [3275423817|https://gitlab.com/womcorp/wom-digital/core/tmf/libraries/wom-cl-domain/-/jobs/3275423817] |
    | compile (build_package) | [3275423816|https://gitlab.com/womcorp/wom-digital/core/tmf/libraries/wom-cl-domain/-/jobs/3275423816] |
    | version (version) | [3275423814|https://gitlab.com/womcorp/wom-digital/core/tmf/libraries/wom-cl-domain/-/jobs/3275423814] |
