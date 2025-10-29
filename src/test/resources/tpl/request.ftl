<html>
  <#-- @ftlvariable name="data" type="io.qameta.allure.attachment.http.HttpRequestAttachment" -->
  <head>
    <meta charset="UTF-8">
    <title>Request</title>
    <style>
      :root {
        --border: #e1e4e8;
        --bg-page: #f8f8f9;
        --bg-card: #f6f8fa;
        --text: #24292e;
        --mono: ui-monospace, SFMono-Regular, Menlo, Monaco, Consolas, "Liberation Mono", "Courier New", monospace;
        --radius: 6px;
        --space: 20px;
      }
      html, body { margin: 0; padding: 0; color: var(--text); background: var(--bg-page); }
      body { font: 14px/1.5 -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Helvetica, Arial; padding: var(--space); }
      .block { border: 1px solid var(--border); border-radius: var(--radius); background: #fff; margin: 0; }
      .block + .block { margin-top: var(--space); }
      .block__title { background: var(--bg-card); padding: 10px 14px; border-bottom: 1px solid var(--border); font-weight: 600; }
      .block__body { padding: 12px 14px; }
      pre, code { font-family: var(--mono); font-size: 12px; }
      pre { margin: 0; white-space: pre-wrap; word-break: break-word; }
      .kv { margin: 0; }
      .kv + .kv { margin-top: 6px; }
      .kv b { font-weight: 600; }
    </style>
  </head>
  <body>
    <div class="block">
      <div class="block__title">Request</div>
      <div class="block__body">
        <#if (data.method??) || (data.url??)>
          <pre><code><#if data.method??>${data.method?html}</#if><#if data.url??> ${data.url?html}</#if></code></pre>
        </#if>
      </div>
    </div>

    <#if (data.headers)?has_content>
      <div class="block">
        <div class="block__title">Headers</div>
        <div class="block__body">
          <#list data.headers as name, value>
            <pre class="kv"><code><b>${name?html}</b>: ${value?html}</code></pre>
          </#list>
        </div>
      </div>
    </#if>

    <#if data.body??>
      <div class="block">
        <div class="block__title">Body</div>
        <div class="block__body">
          <pre><code>${data.body?html}</code></pre>
        </div>
      </div>
    </#if>

    <#if (data.cookies)?has_content>
      <div class="block">
        <div class="block__title">Cookies</div>
        <div class="block__body">
          <#list data.cookies as name, value>
            <pre class="kv"><code><b>${name?html}</b>: ${value?html}</code></pre>
          </#list>
        </div>
      </div>
    </#if>

    <#if data.curl??>
      <div class="block">
        <div class="block__title">Curl</div>
        <div class="block__body">
          <pre><code>${data.curl?html}</code></pre>
        </div>
      </div>
    </#if>

    <script>
      window.addEventListener('load', function() {
        try {
          if (window.frameElement) {
            var h = Math.max(
              document.documentElement.scrollHeight || 0,
              document.body.scrollHeight || 0
            );
            window.frameElement.style.height = h + 'px';
          }
        } catch (e) {
          console.warn('iframe resize skipped:', e);
        }
      });
    </script>
  </body>
</html>
