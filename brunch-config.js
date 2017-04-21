exports.config = {
  files: {
    stylesheets: {
      joinTo: "css/app.css",
      order: {
        after: ["resources/sass/app.scss"]
      }
    }
  },

  conventions: {
    assets: /^(resources\/public)/
  },

  paths: {
    watched: [
      "resources/sass"
    ],

    public: "resources/public"
  },

  plugins: {
    copycat: {
      "fonts": ["node_modules/bootstrap-sass/assets/fonts/bootstrap"]
    },
    sass: {
      options: {
        includePaths: ["node_modules/bootstrap-sass/assets/stylesheets"],
        precision: 8
      }
    }
  },

  npm: {
    enabled: true,
    globals: {
      $: 'jquery',
      jQuery: 'jquery',
      bootstrap: 'bootstrap-sass'
    }
  }
};
