<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript">
    var planId = "${schemeid}";
    if(planId !=undefined && planId != "" ) {
        window.location.href="../../#!/static/html/plan/planDetailsV2.html?planId=${schemeid}&path=plan";
    } else {
        window.location.href="../../#!/static/html/popular/financesList.html?path=popular";
    }
</script>
