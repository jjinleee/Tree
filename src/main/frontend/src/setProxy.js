
module.exports = function(app) {
    app.use(
      '/member/login',
      createProxyMiddleware({
        target: 'http://localhost:8081',
        changeOrigin: true,
      })
    );
  };